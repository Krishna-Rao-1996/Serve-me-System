package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.Email.EmailServices;

public class ForgotPassword extends Activity {

    EditText emailEditText;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailEditText = findViewById(R.id.emailEditText);
        submitButton = findViewById(R.id.submitButton);
        Log.i("here   ->", "on create");
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendEmail();
                    }
                }
        );

    }

    protected void sendEmail() {
        EmailServices emailServices = new EmailServices(ForgotPassword.this, emailEditText.getText().toString());
        Log.i("here   ->",emailEditText.getText().toString());
        emailServices.execute();
        Toast.makeText(ForgotPassword.this, "Email sent...", Toast.LENGTH_SHORT).show();
    }
}
