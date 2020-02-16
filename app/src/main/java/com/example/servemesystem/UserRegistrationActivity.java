package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
    String password, fullName, userName, email, phone, city, state, country, dateOfBirth, confirmPass;
    EditText pass, fname, uname, email1, ph, city1, country1, dob, cpass;
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
                boolean flag = true;

                    if(emailExists(email)){
                        email1.setText("");
                        email1.setHint("E-mail already exists");
                        flag=false;
                    }
                    if(phoneExists(phone)){
                        ph.setText("");
                        ph.setHint("Phone number already exists");
                        flag=false;
                    }
                    if (usernameExists(userName)) {
                        uname.setText("");
                        uname.setHint("User already exists");
                        flag = false;
                    }
                    if (!verifyName(fullName)) {
                        fname.setText("");
                        fname.setHint("Name can have only alphabets");
                        flag = false;
                    }
                    if (!verifyPhone(phone)) {
                        ph.setText("");
                        ph.setHint("Enter a valid 10 digit phone number");
                        flag = false;
                    }
                    if (!verifyPassword(password)) {
                        pass.setText("");
                        pass.setHint("Enter at least 6 characters");
                        flag = false;
                    }
                    if (!verifyConfirmPass(password, confirmPass)) {
                        cpass.setText("");
                        cpass.setHint("Passwords don't match");
                        flag = false;
                    }
                    if (!verifyEmail(email)) {
                        email1.setText("");
                        email1.setHint("Enter a valid e-mail address");
                        flag = false;
                    }
                    if (!verifydob(dateOfBirth)) {
                        dob.setText("");
                        dob.setHint("Enter a valid date");
                        flag = false;
                    }

                    if (flag) {
                        sendData();
                    }

            }
        });

    }

    @Override
    void sendData() {
        // Write a message to the database

        Map mymap = new HashMap<>();
        mymap.put("FullName", fullName);
        mymap.put("Phone", phone);
        mymap.put("DateOfBirth", dateOfBirth);
        mymap.put("Email", email);
        mymap.put("City", city);
        mymap.put("State", state);
        mymap.put("Country", country);
        mymap.put("Password", password);
        myRef.child("Users").child(userName).updateChildren(mymap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Map userCred = new HashMap<>();
        userCred.put(userName, password);
        myRef.child("User_Credentials").updateChildren(userCred, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
    }

}
