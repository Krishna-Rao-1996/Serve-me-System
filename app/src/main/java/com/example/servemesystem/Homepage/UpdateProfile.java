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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servemesystem.LoginActivity;
import com.example.servemesystem.R;
import com.example.servemesystem.RegistrationHelper;
import com.example.servemesystem.UserModel;
import com.example.servemesystem.UserRegistrationActivity;
import com.example.servemesystem.domain.ConstantResources;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends RegistrationHelper {
    CircleImageView imageview_account_profile;
    TextView updateUserNameTV;
    EditText updateFNameTV, updateLNameTV, updatePhoneTV, updateEmailTV, updateAddressTV, updateCityTV, updateStateTV, updateZipTV,
            updateCompanyNameTV, updateCompanyAddressTV, updateCompanyCityTV, updateCompanyPhoneTV;
    String updateFNameTVString, updateLNameTVString, updatePhoneTVString, updateEmailTVString, updateAddressTVString, updateCityTVString,
            updateStateTVString, updateZipTVString,updateCompanyNameTVString, updateCompanyAddressTVString,
            updateCompanyCityTVString, updateCompanyPhoneTVString;
    Button updateProfileBtn;
    LinearLayout serviceProviderUpdateLayout;
    static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference myServref = FirebaseDatabase.getInstance().getReference("Service_Providers");
    final DatabaseReference myUserref = FirebaseDatabase.getInstance().getReference("Users");
    SharedPreferences sharedPreferences;
    Boolean flag = true;

    String userName;
    String userType;
    HashMap<String, UserModel> allUsers= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        sharedPreferences = getSharedPreferences("currUser", MODE_PRIVATE);
        userName = sharedPreferences.getString("userName", null);
        userType = sharedPreferences.getString("type",null);

        updateProfileBtn = findViewById(R.id.updateProfileBtn);
        updateUserNameTV = findViewById(R.id.updateUserNameTV);
        imageview_account_profile =findViewById(R.id.imageview_account_profile);
        updateFNameTV = findViewById(R.id.updateFNameTV);
        updatePhoneTV= findViewById(R.id.updatePhoneTV);
        updateEmailTV= findViewById(R.id.updateEmailTV);
        updateAddressTV= findViewById(R.id.updateAddressTV);
        updateCityTV= findViewById(R.id.updateCityTV);
        updateStateTV= findViewById(R.id.updateStateTV);
        updateZipTV= findViewById(R.id.updateZipTV);
        updateCompanyNameTV= findViewById(R.id.updateCompanyNameTV);
        updateCompanyAddressTV= findViewById(R.id.updateCompanyAddressTV);
        updateCompanyCityTV= findViewById(R.id.updateCompanyCityTV);
        updateCompanyPhoneTV= findViewById(R.id.updateCompanyPhoneTV);


        Log.e("USERNAME: ", userName);

        if("user".equalsIgnoreCase(userType))
        {

            DatabaseReference ref1 = myUserref.child(userName);

            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    updateUserNameTV.setText(userName);
                    updateFNameTVString = dataSnapshot.child("FullName").getValue(String.class);
                    updateFNameTV.setText(updateFNameTVString);
                    updatePhoneTVString = dataSnapshot.child("Phone").getValue(String.class);
                    updatePhoneTV.setText(updatePhoneTVString);
                    updateEmailTVString = dataSnapshot.child("Email").getValue(String.class);
                    updateEmailTV.setText(updateEmailTVString);
                    updateAddressTVString = dataSnapshot.child("Address").getValue(String.class);
                    updateAddressTV.setText(updateAddressTVString);
                    updateCityTVString = dataSnapshot.child("City").getValue(String.class);
                    updateCityTV.setText(updateCityTVString);
                    updateStateTVString = dataSnapshot.child("State").getValue(String.class);
                    updateStateTV.setText(updateStateTVString);
                    updateZipTVString = dataSnapshot.child("Zipcode").getValue(String.class);
                    updateZipTV.setText(updateZipTVString);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });
        }
        else
        {
            DatabaseReference ref2 = myServref.child(userName);

            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot data)
                {
                    updateUserNameTV.setText(userName);
                    updateFNameTVString = data.child("FullName").getValue(String.class);
                    updateFNameTV.setText(updateFNameTVString);
                    updatePhoneTVString = data.child("Phone").getValue(String.class);
                    updatePhoneTV.setText(updatePhoneTVString);
                    updateEmailTVString = data.child("Email").getValue(String.class);
                    updateEmailTV.setText(updateEmailTVString);
                    updateAddressTVString = data.child("Address").getValue(String.class);
                    updateAddressTV.setText(updateAddressTVString);
                    updateCityTVString = data.child("City").getValue(String.class);
                    updateCityTV.setText(updateCityTVString);
                    updateStateTVString = data.child("State").getValue(String.class);
                    updateStateTV.setText(updateStateTVString);
                    updateZipTVString = data.child("Zipcode").getValue(String.class);
                    updateZipTV.setText(updateZipTVString);
                    updateCompanyAddressTVString = data.child("Officeaddress").getValue(String.class);
                    updateCompanyAddressTV.setText(updateCompanyAddressTVString);
                    updateCompanyNameTVString = data.child("Companyname").getValue(String.class);
                    updateCompanyNameTV.setText(updateCompanyNameTVString);
                    updateCompanyPhoneTVString = data.child("Officenumber").getValue(String.class);
                    updateCompanyPhoneTV.setText(updateCompanyPhoneTVString);
                    updateCompanyCityTVString = data.child("Officecity").getValue(String.class);
                    updateCompanyCityTV.setText(updateCompanyCityTVString);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });
        }


        if("user".equalsIgnoreCase(userType)){
            serviceProviderUpdateLayout = findViewById(R.id.serviceProviderUpdateLayout);
            serviceProviderUpdateLayout.setVisibility(View.GONE);
        }

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateFNameTVString = updateFNameTV.getText().toString();
                updatePhoneTVString = updatePhoneTV.getText().toString();
                updateEmailTVString = updateEmailTV.getText().toString();
                updateAddressTVString = updateAddressTV.getText().toString();
                updateCityTVString = updateCityTV.getText().toString();
                updateZipTVString = updateZipTV.getText().toString();
                if(!"user".equalsIgnoreCase(userType))
                {
                    updateCompanyAddressTVString = updateCompanyAddressTV.getText().toString();
                    updateCompanyNameTVString = updateCompanyNameTV.getText().toString();
                    updateCompanyPhoneTVString = updateCompanyPhoneTV.getText().toString();
                    updateCompanyCityTVString = updateCompanyCityTV.getText().toString();
                }
                sendData();
            }
        });

        imageview_account_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),1234);
            }
        });
    }

    private void fetchDataa() {

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

    @Override
    protected void sendData()
    {
        if("user".equalsIgnoreCase(userType))
        {

           final DatabaseReference  userRef = FirebaseDatabase.getInstance().getReference().child("Users");

            userRef.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    userRef.child(userName).child("FullName").setValue(updateFNameTVString);
                    userRef.child(userName).child("Phone").setValue(updatePhoneTVString);
                    userRef.child(userName).child("Email").setValue(updateEmailTVString);
                    userRef.child(userName).child("Address").setValue(updateAddressTVString);
                    userRef.child(userName).child("City").setValue(updateCityTVString);
                    userRef.child(userName).child("State").setValue(updateStateTVString);
                    userRef.child(userName).child("Zipcode").setValue(updateZipTVString);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });

            Toast.makeText(getApplicationContext(), "Values Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(UpdateProfile.this, LoginActivity.class);
            startActivity(login);
        }
        else
        {
            final DatabaseReference  servRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");

            servRef.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    servRef.child(userName).child("FullName").setValue(updateFNameTVString);
                    servRef.child(userName).child("Phone").setValue(updatePhoneTVString);
                    servRef.child(userName).child("Email").setValue(updateEmailTVString);
                    servRef.child(userName).child("Address").setValue(updateAddressTVString);
                    servRef.child(userName).child("City").setValue(updateCityTVString);
                    servRef.child(userName).child("State").setValue(updateStateTVString);
                    servRef.child(userName).child("Zipcode").setValue(updateZipTVString);
                    servRef.child(userName).child("Officeaddress").setValue(updateCompanyAddressTVString);
                    servRef.child(userName).child("Companyname").setValue(updateCompanyNameTVString);
                    servRef.child(userName).child("Officenumber").setValue(updateCompanyPhoneTVString);
                    servRef.child(userName).child("Officecity").setValue(updateCompanyCityTVString);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    Log.e("The read failed: ", databaseError.getMessage());
                }
            });

            Toast.makeText(getApplicationContext(), "Values Updated Successfully", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(UpdateProfile.this, LoginActivity.class);
            startActivity(login);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                        imageview_account_profile.setImageBitmap(bitmap);
                        myRef.child("userName").child("dp").setValue(bitmap.toString());

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
