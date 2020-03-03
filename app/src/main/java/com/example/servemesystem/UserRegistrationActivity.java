package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.domain.ConstantResources;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class UserRegistrationActivity extends RegistrationHelper {
    String password, fullName, userName, email, phone, city, state, country, dateOfBirth, confirmPass,address;
    EditText pass, fname, uname, email1, ph, city1, address1, dob, cpass;
    Spinner state1;
    Button registration;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> userDetails;
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        pass = findViewById(R.id.pass1);
        cpass = findViewById(R.id.conpass1);
        fname = findViewById(R.id.fname1);
        uname = findViewById(R.id.sname1);
        email1 = findViewById(R.id.email1);
        ph = findViewById(R.id.phone1);
        state1 = findViewById(R.id.state1);
        city1 = findViewById(R.id.city1);
        address1=findViewById(R.id.address1);
        dob = findViewById(R.id.dob1);
        registration = findViewById(R.id.userReg);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userDetails = new ArrayList<>();
        fetchData();
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = getPassword(pass);
                fullName = getFullName(fname);
                userName = getUserName(uname);
                email = getEmail(email1);
                phone = getPhone(ph);
                city = getCity(city1);
                state = getState(state1);
                dateOfBirth = getDateOfBirth(dob);
                confirmPass = getConfirmPass(cpass);
                address=getAddress(address1);
                boolean flag = true;

                    if(emailExists(email)){
                        email1.setText("");
                        email1.setError("E-mail already exists");
                        flag=false;
                    }
                    if(userName.length()<1){
                        uname.setText("");
                        uname.setError("Enter a Username");
                        flag=false;
                    }
                    if(state.equals("Select a State")){
                        ((TextView)state1.getSelectedView()).setError("Select a State");
                        flag=false;
                    }
                    if(!verifyUsername(userName)){
                        uname.setText("");
                        uname.setError("Please enter only alphanumeric values");
                        flag=false;
                    }
                    if(city.length()<1){
                        city1.setText("");
                        city1.setError("Enter a City");
                        flag=false;
                        }
                    if(confirmPass.length()<1){
                        cpass.setText("");
                        cpass.setError("Confirm password can't be empty");
                        flag=false;
                    }
                    if(phoneExists(phone)){
                        ph.setText("");
                        ph.setError("Phone number already exists");
                        flag=false;
                    }
                    if (usernameExists(userName)) {
                        uname.setText("");
                        uname.setError("User already exists");
                        flag = false;
                    }
                    if (!verifyName(fullName)) {
                        fname.setText("");
                        fname.setError("Name can have only alphabets");
                        flag = false;
                    }
                    if(!verifyAddress(address)){
                        address1.setText("");
                        address1.setError("Enter an Address");
                        flag=false;
                    }
                    if (!verifyPhone(phone)) {
                        ph.setText("");
                        ph.setError("Enter a valid 10 digit phone number");
                        flag = false;
                    }
                    if (!verifyPassword(password)) {
                        pass.setText("");
                        pass.setError("Enter at least 6 characters");
                        flag = false;
                    }
                    if (!verifyConfirmPass(password, confirmPass)) {
                        cpass.setText("");
                        cpass.setError("Passwords don't match");
                        flag = false;
                    }
                    if (!verifyEmail(email)) {
                        email1.setText("");
                        email1.setError("Enter a valid e-mail address");
                        flag = false;
                    }
                    if (!verifydob(dateOfBirth)) {
                        dob.setText("");
                        dob.setError("Enter a valid date");
                        flag = false;
                    }

                    if (flag) {
                        sendData();
                    }

            }
        });

    }
    @Override
    protected void sendData() {
        // Write a message to the database

        Map mymap = new HashMap<>();
        mymap.put("FullName", fullName);
        mymap.put("Phone", phone);
        mymap.put("DateOfBirth", dateOfBirth);
        mymap.put("Email", email);
        mymap.put("City", city);
        mymap.put("State", state);
        mymap.put("Address", address);
        mymap.put("ResetPIN","NULL");
        mymap.put("dp","");
        mymap.put("Password", password);
        myRef.child(ConstantResources.USERS).child(userName).updateChildren(mymap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Map userCred = new HashMap<>();
        userCred.put(userName, password);
        myRef.child(ConstantResources.USER_CREDENTIALS).updateChildren(userCred, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
        Intent login = new Intent(UserRegistrationActivity.this,LoginActivity.class);
        startActivity(login);
    }

}
