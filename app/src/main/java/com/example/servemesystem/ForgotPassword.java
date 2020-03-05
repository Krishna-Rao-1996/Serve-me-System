package com.example.servemesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servemesystem.Email.EmailServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ForgotPassword extends RegistrationHelper {

    EditText emailEditText;
    Button submitButton;
    Button submitButtonVerify;
    EditText verifyPIN;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    static final String TAG ="ForgotPassword :";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        verifyPIN.setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Log.i(TAG, "on create");
        RegistrationHelper helper = new RegistrationHelper() {
            @Override
            protected void sendData() {
                return;
            }
        };

        submitButton = findViewById(R.id.submitResetPassword);
        emailEditText = findViewById(R.id.emailEditText);
        verifyPIN = findViewById(R.id.verifyPIN);
        submitButtonVerify = findViewById(R.id.submitButtonVerify);
//        fetchData();
//        allUsers.containsKey("Yayi");
        submitButton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (verifyEmail(emailEditText.getText().toString())) { //verification to check email field not empty
                            sendEmail();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Please enter correct email", Toast.LENGTH_SHORT).show();
                            emailEditText.getText().clear();
                        }
                    }

                }
        );
        submitButtonVerify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        String recoveryPin = getIntent().getExtras().getString("recoveryPin");
//                        String recoveryPin = bundle.getString("recoveryPin");

//                        Toast.makeText(ForgotPassword.this, recoveryPin, Toast.LENGTH_SHORT).show();
                        String enteredPIN = verifyPIN.getText().toString();
                        if (!enteredPIN.equals("1234") && !enteredPIN.isEmpty()){
                            Intent intent = new Intent(ForgotPassword.this, ResetPasswordActivity.class);
                            startActivity(intent);
//                            fetchData();
                        } else if (enteredPIN.isEmpty()){
                            Toast.makeText(ForgotPassword.this, "Please enter PIN", Toast.LENGTH_SHORT).show();
                            verifyPIN.getText().clear();
                        }
                        else if(!enteredPIN.isEmpty()){
                            Toast.makeText(ForgotPassword.this, "Please enter correct PIN", Toast.LENGTH_SHORT).show();
                            verifyPIN.getText().clear();
                        }
                    }
                }
        );
    }


    //    public void fetchData(){
//
//        HashMap<String, UserModel> allUsers= new HashMap<>();
//        final ArrayList<UserModel> userValues;
//        ArrayList<String> emailPhone = new ArrayList<>();
//        final Array[] userName = new Array[2];
//        dbRef.child("User_Credentials").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//
//                String post, uname;
//                Log.e("Count ", "" + snapshot.getChildrenCount());
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    post = postSnapshot.getValue(String.class);
//                    uname = postSnapshot.getKey();
////                    allUsers.put(uname, post);
//                    if (uname == userName[0].toString()){
//                        //get username from below function and store it into the array at [0], uname will be username from User Creds
//                    }
//
//                }
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    UserModel postUsers = postSnapshot.getValue(UserModel.class);
//                    String unameUsers = postSnapshot.getKey();
//                }
//                userValues = new ArrayList<>(allUsers.values());
//                for(int i=0;i<userValues.size();i++){
//                    if (emailEditText.getText().toString() == userValues.get(i).getEmail()){
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError firebaseError) {
//                Log.e("The read failed: ", firebaseError.getMessage());
//            }
//        });
//    }

    public boolean verifyEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        Log.i(TAG, "verifyEmail");

        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    protected void sendData() {
        return ;
    }


    protected void sendEmail() {
        EmailServices emailServices = new EmailServices(ForgotPassword.this, emailEditText.getText().toString());
        Log.i(TAG, emailEditText.getText().toString());
        emailServices.execute();
        Log.i(TAG, "sendEmail");

        //Toast.makeText(ForgotPassword.this, "Email sent", Toast.LENGTH_SHORT).show();

        //hide email field and button field after successful email sending
        setVisibilityOfEmail();
        verifyPIN();
    }


    protected void verifyPIN(){
        EditText verifyPIN = findViewById(R.id.verifyPIN);
        verifyPIN.setVisibility(View.VISIBLE);
        submitButtonVerify = findViewById(R.id.submitButtonVerify);
        submitButtonVerify.setVisibility(View.VISIBLE);

//        verifyPIN.getText();
        if (verifyPIN.getText().toString() == "1234"){
            //navigating to Login activity if success
            Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
            startActivity(intent);
        }
        else if(!verifyPIN.getText().toString().isEmpty()){
            Toast.makeText(this, "Entered PIN does not match", Toast.LENGTH_SHORT).show();
        }
    }


    protected void setVisibilityOfEmail(){
        submitButton = findViewById(R.id.submitResetPassword);
        submitButton.setVisibility(View.INVISIBLE);
        Log.i(TAG, "setVisibilityOfEmail");

        emailEditText = findViewById(R.id.emailEditText);
        emailEditText.setVisibility(View.INVISIBLE);
    }



}
