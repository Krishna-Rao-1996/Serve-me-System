package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends Activity {
    private Button userReg,spReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userRegistration();
        serviceProviderRegistration();
    }
    private void userRegistration() {
        userReg = findViewById(R.id.userButton);
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(RegisterActivity.this, UserRegistrationActivity.class);
                startActivity(myint);
            }
        });
    }
        private void serviceProviderRegistration() {
            spReg = findViewById(R.id.spButton);
            spReg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myint = new Intent(RegisterActivity.this, ServiceProviderRegistrationActivity.class);
                    startActivity(myint);
                }
            });
        }
}
