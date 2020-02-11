package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
    private Button userReg;
    private TextView forgotPwdTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register();
        forgotPwdTV = findViewById(R.id.textView2);
        forgotPwdTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(myint);
            }
        });

    }

    private void register() {
        userReg=findViewById(R.id.regbutton);
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(myint);
            }
        });
    }
}
