package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ViewServiceRequest extends Activity {
    TextView userNameTV,requestTV,requestMessageTV;
    Button accept,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_request);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String requestName = intent.getStringExtra("serviceReq");
        String userMessage= intent.getStringExtra("userMessage");
        userNameTV=findViewById(R.id.userNameSR);
        requestTV=findViewById(R.id.requestNameSR);
        requestMessageTV=findViewById(R.id.userMessageSR);
        accept=findViewById(R.id.acceptButton);
        reject=findViewById(R.id.rejectButton);
        userNameTV.setText(userName);
        requestTV.setText(requestName);
        requestMessageTV.setText(userMessage);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request Accepted",Toast.LENGTH_SHORT).show();

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Request Rejected",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
