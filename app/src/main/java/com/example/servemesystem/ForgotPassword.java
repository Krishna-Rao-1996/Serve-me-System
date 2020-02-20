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

import java.util.regex.Pattern;

public class ForgotPassword extends Activity {

    EditText emailEditText;
    Button submitButton;
    Button submitButtonVerify;
    EditText verifyPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        verifyPIN.setVisibility(View.INVISIBLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Log.i("here   ->", "on create");
        submitButton = findViewById(R.id.submitResetPassword);
        emailEditText = findViewById(R.id.emailEditText);
        verifyPIN = findViewById(R.id.verifyPIN);
        submitButtonVerify = findViewById(R.id.submitButtonVerify);
        submitButton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (verifyEmail(emailEditText.getText().toString())) { //verification to check email field not empty
                            sendEmail();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Please enter correct email", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );
        submitButtonVerify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String enteredPIN = verifyPIN.getText().toString();
                        if (enteredPIN.equals("1234")){
                            Intent intent = new Intent(ForgotPassword.this, ResetPasswordActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Please enter correct PIN", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }



    public boolean verifyEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        Log.i("here   ->", "verifyEmail");

        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    protected void sendEmail() {
        EmailServices emailServices = new EmailServices(ForgotPassword.this, emailEditText.getText().toString());
        Log.i("here   ->",emailEditText.getText().toString());
        emailServices.execute();
        Log.i("here   ->", "sendEmail");

        Toast.makeText(ForgotPassword.this, "Email sent", Toast.LENGTH_SHORT).show();

        //hide email field and button field after successful email sending
        setVisibilityOfEmail();
        verifyPIN();
    }


    protected void verifyPIN(){
        EditText verifyPIN = findViewById(R.id.verifyPIN);
        verifyPIN.setVisibility(View.VISIBLE);
        submitButtonVerify = findViewById(R.id.submitButtonVerify);
        submitButtonVerify.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Entered VerifyPIN", Toast.LENGTH_SHORT).show();

//        verifyPIN.getText();
        if (verifyPIN.getText().toString() == "1234"){
            //navigating to Login activity if success
            Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Entered PIN does not match", Toast.LENGTH_SHORT).show();
        }
    }


    protected void setVisibilityOfEmail(){
        submitButton = findViewById(R.id.submitResetPassword);
        submitButton.setVisibility(View.INVISIBLE);
        Log.i("here   ->", "setVisibilityOfEmail");

        emailEditText = findViewById(R.id.emailEditText);
        emailEditText.setVisibility(View.INVISIBLE);
    }



}
