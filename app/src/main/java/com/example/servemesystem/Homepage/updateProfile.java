package com.example.servemesystem.Homepage;

import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.R;
import com.example.servemesystem.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

public class updateProfile extends Activity {
    CircleImageView profilePicture;
    EditText fullName,email,phone,password;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String userName;
    HashMap<String, UserModel> allUsers= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        profilePicture =findViewById(R.id.imageview_account_profile);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1234);
            }
        });

        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        userName = prefs.getString("userName", null);//"No name defined" is the default value.
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        fullName= findViewById(R.id.changeFullName);
        email= findViewById(R.id.changeEmail);
        phone= findViewById(R.id.changePhone);
        password= findViewById(R.id.changePassword);
        fetchData();
        UserModel currtemp = allUsers.get(userName);
        email.setText(currtemp.getEmail());
        fullName.setText(currtemp.getFullName());
        password.setText(currtemp.getPassword());
        phone.setText(currtemp.getPhone());

    }

    private void fetchData() {

        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count ", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UserModel post = postSnapshot.getValue(UserModel.class);
                    String uname = postSnapshot.getKey();
                    allUsers.put(uname, post);
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        profilePicture.setImageBitmap(bitmap);
                        myRef.child(userName).child("dp").setValue(bitmap.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
