package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.Homepage.UserHomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlaceServiceRequest extends Activity {

    String TAG = "PlaceServiceRequest.java";
    Context context= PlaceServiceRequest.this;
    String problemImageURL="", fileName;
    TextView serviceType;
    EditText message;
    ImageView userProblemImage;
    Button submitRequestButton, takePictureButton;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Uri file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_service_request);
        Log.i(TAG, "Inside PlaceServiceRequest.java");
        Intent intent = getIntent();
        final String serviceRequestName = intent.getStringExtra("serviceRequestName");
        serviceType = findViewById(R.id.serviceType);
        serviceType.setText(serviceRequestName);
        message = findViewById(R.id.problemDescription);
        submitRequestButton = findViewById(R.id.submitRequestButton);

        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "inside onClick Request Button"+serviceRequestName);
                if (checkMessage(message.getText().toString())){
                    submitServiceRequest(serviceRequestName, message.getText().toString());
                }
                else {
                    message.setError("Please describe your problem");
                }
            }
        });
    }

    private boolean checkMessage(String message) {
        if ( message.length() != 0 && message.length()<=200) {
            return true;
        }
        else return false;
    }


    private void submitServiceRequest(String serviceRequestName, String message) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        String uname = prefs.getString("userName", "");
        Log.i(TAG,  "inside submitServiceRequest");
        Map mymap = new HashMap<>();
        mymap.put("", uname);
        databaseReference.child("ServiceRequests").child(serviceRequestName).updateChildren(mymap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Map mapInsideServiceRequests = new HashMap<>();
        mapInsideServiceRequests.put("Description", message);
        if (!problemImageURL.isEmpty()){
            mapInsideServiceRequests.put("ServiceProblemImage",problemImageURL);
        }
        databaseReference.child("ServiceRequests").child(serviceRequestName).child(uname).updateChildren(mapInsideServiceRequests, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
                }
            }
        });
        Toast.makeText(this, "Request successfully submitted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PlaceServiceRequest.this, UserHomeActivity.class);
        startActivity(intent);

    }

    public void takePicture(View view) {
        Log.i(TAG,  "inside takePicture");
        takePictureButton = (Button) findViewById(R.id.userProblemButton);
        userProblemImage = (ImageView) findViewById(R.id.userProblemImage);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        startActivityForResult(intent, 100);
        intent.setType("image/*");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,  "inside onActivityResult");
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        final String uname = prefs.getString("userName", "");
        storageReference = FirebaseStorage.getInstance().getReference("images").child(uname);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        userProblemImage = findViewById(R.id.userProblemImage);
            if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                userProblemImage.setImageBitmap(imageBitmap);
                Uri uri = getImageUri(context, imageBitmap);
                if(userProblemImage.getDrawable() != null){
                    final String serviceName = serviceType.getText().toString();
                    fileName = serviceName+"."+getFileExtention(uri);
                    storageReference = storageReference.child(serviceName+"."+getFileExtention(uri));
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String urlMaker = "https://firebasestorage.googleapis.com/v0/b/serveme-system-d313f.appspot.com/o/images/+"+uname+"%2F"+serviceName+".jpg?alt=media";
                            databaseReference.child(serviceName).child(uname).child("ServiceProblemImage").setValue(urlMaker);
                            problemImageURL = urlMaker;
//                            problemImageURL = taskSnapshot.getTask().getResult().toString();
                            Toast.makeText(PlaceServiceRequest.this, "URL"+problemImageURL, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Upload Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Toast.makeText(this, "Image taken successfully", Toast.LENGTH_SHORT).show();
            }
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG,  "inside onRequestPermissionsResult");
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }


    public File getOutputMediaFile(){
        Log.i(TAG,  "inside getOutputMediaFile");
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Serve-Me-System");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }
        String pathnameBuilder = mediaStorageDir.getPath() + File.separator + serviceType.getText().toString() + ".jpg";
        return new File(pathnameBuilder);
    }

}
