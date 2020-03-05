package com.example.servemesystem.Homepage;

import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servemesystem.R;
import com.example.servemesystem.UserModel;
import com.example.servemesystem.domain.ConstantResources;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends Activity {

    CircleImageView imageview_account_profile;
    TextView updateUserNameTV;
    EditText updateFNameTV, updatePhoneTV, updateEmailTV, updateAddressTV, updateCityTV, updateStateTV, updateZipTV,
            updateCompanyNameTV, updateCompanyAddressTV, updateCompanyCityTV, updateCompanyPhoneTV;
    Button updateProfileBtn;
    LinearLayout serviceProviderUpdateLayout;
    SharedPreferences sharedPreferences;

    private static int IMAGE_REQUEST_CODE = 1;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference updateFirebaseRef;

    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("images");

    private Uri imageURI;
    private String fileName;
    private String dpURL;

    String userName;
    String userType;
    HashMap<String, UserModel> allUsers= new HashMap<>();
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        sharedPreferences = getSharedPreferences("currUser", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        userType = sharedPreferences.getString("type",null);

        updateUserNameTV = findViewById(R.id.updateUserNameTV);
        imageview_account_profile =findViewById(R.id.imageview_account_profile);
        updateFNameTV = findViewById(R.id.updateFNameTV);
        updatePhoneTV= findViewById(R.id.updatePhoneTV);
        updateEmailTV= findViewById(R.id.updateEmailTV);
        updateAddressTV= findViewById(R.id.updateAddressTV);
        updateCityTV= findViewById(R.id.updateCityTV);
        updateStateTV= findViewById(R.id.updateStateTV);
//        updateZipTV= findViewById(R.id.updateZipTV);
        updateCompanyNameTV= findViewById(R.id.updateCompanyNameTV);
        updateCompanyAddressTV= findViewById(R.id.updateCompanyAddressTV);
        updateCompanyCityTV= findViewById(R.id.updateCompanyCityTV);
        updateCompanyPhoneTV= findViewById(R.id.updateCompanyPhoneTV);
        updateProfileBtn = findViewById(R.id.updateProfileBtn);

        if(ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)){
            serviceProviderUpdateLayout = findViewById(R.id.serviceProviderUpdateLayout);
            serviceProviderUpdateLayout.setVisibility(View.GONE);
        }

        updateFirebaseRef = ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)?
                                myRef.child(ConstantResources.USERS).child(userName):
                                myRef.child(ConstantResources.SERVICE_PROVIDER).child(userName);
        updateFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("dp").exists()){
                    Picasso.get().load(dataSnapshot.child("dp").getValue().toString()).into(imageview_account_profile);
                }
                updateUserNameTV.setText(dataSnapshot.getKey().toString());
                if(ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)){
                    updateFNameTV.setText(dataSnapshot.child("FullName").getValue().toString());
                    updateAddressTV.setText(dataSnapshot.child("Address").getValue().toString());
                } else {
                    updateFNameTV.setText(dataSnapshot.child("FirstName").getValue().toString());
                    updateAddressTV.setText(dataSnapshot.child("Officeaddress").getValue().toString());
                }

                updatePhoneTV.setText(dataSnapshot.child("Phone").getValue().toString());
                updateEmailTV.setText(dataSnapshot.child("Email").getValue().toString());
                updateCityTV.setText(dataSnapshot.child("City").getValue().toString());
                updateStateTV.setText(dataSnapshot.child("State").getValue().toString());
                if(!ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)) {
                    updateCompanyNameTV.setText(dataSnapshot.child("Companyname").getValue().toString());
                    updateCompanyAddressTV.setText(dataSnapshot.child("Officeaddress").getValue().toString());
//                    updateCompanyCityTV.setText(dataSnapshot.child("City").getValue().toString());
                    updateCompanyPhoneTV.setText(dataSnapshot.child("Officenumber").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        imageview_account_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),IMAGE_REQUEST_CODE);
            }

        });

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            imageURI = data.getData();
            imageview_account_profile.setImageURI(imageURI );
        }
        else if (resultCode == Activity.RESULT_CANCELED)  {
            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final StorageReference fileRef;
        if(imageURI != null){
            fileName = userName+"."+getFileExtention(imageURI);
            fileRef = mStorageRef.child(userName+"."+getFileExtention(imageURI));
            fileRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //had to do this since I wasn't getting the Downloadable HTTP link for the images being uploaded in firebase storage
                    updateFirebaseRef.child("dp").setValue("https://firebasestorage.googleapis.com/v0/b/serveme-system-d313f.appspot.com/o/images%2F"+userName+".jpg?alt=media");
                    dpURL = taskSnapshot.getTask().getResult().toString();
                    updateData();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Upload Fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            updateData();
        }
    }

    private void updateData(){

        Map updateMap = new HashMap<>();
        if(ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)){
            updateMap.put("FullName", updateFNameTV.getText().toString());
            updateMap.put("Address", updateFNameTV.getText().toString());
        } else {
            updateMap.put("FirstName", updateFNameTV.getText().toString());
        }
            updateMap.put("Phone", updatePhoneTV.getText().toString());
            updateMap.put("Email", updateEmailTV.getText().toString());
            updateMap.put("City", updateCityTV.getText().toString());
            updateMap.put("State", updateStateTV.getText().toString());

        if(!ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)){
            updateMap.put("Companyname", updateCompanyNameTV.getText().toString());
            updateMap.put("Officeaddress", updateCompanyAddressTV.getText().toString());
            updateMap.put("Officenumber", updateCompanyPhoneTV.getText().toString());
        }

        updateFirebaseRef.updateChildren(updateMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
            if (databaseError != null) {
                Log.d("CHAT_LOG", databaseError.getMessage().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Profile Successfully updated", Toast.LENGTH_SHORT).show();
                if(ConstantResources.USER_TYPE_CUSTOMER.equalsIgnoreCase(userType)){
                    Intent intent = new Intent(UpdateProfile.this, UserHomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(UpdateProfile.this, ServiceProviderHomeActivity.class);
                    startActivity(intent);
                }
            }
            }
        });
    }

}
