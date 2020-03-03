package com.example.servemesystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.Homepage.UserHomeActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class PlaceServiceRequest extends Activity {

    private String TAG = "PlaceServiceRequest.java";
    private TextView serviceType;
    private EditText message;
    private Button submitRequestButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_service_request);
        Log.i(TAG, "Inside PlaceServiceRequest.java");
        Intent intent = getIntent();
        final String serviceRequestName = intent.getStringExtra("serviceRequestName");
        serviceType = findViewById(R.id.serviceType);
        serviceType.setText(serviceRequestName);
        message = findViewById(R.id.problemDescription);
        submitRequestButton = findViewById(R.id.submitRequestButton);
        submitRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "inside onClick Request Button"+serviceRequestName);
                if (checkMessage(message.getText().toString())){
                    submitServiceRequest(serviceRequestName, message.getText().toString());
                }
                else {
                    message.setError("Please describe your problem");
                }
            }
        });
    }

    private boolean checkMessage(String message) {
        if ( message.length() != 0 && message.length()<=200) {
            return true;
        }
        else return false;
    }


    private void submitServiceRequest(String serviceRequestName, String message) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        String uname = prefs.getString("userName", "");
        Log.i(TAG,  "inside submitServiceRequest");

        Map mymap = new HashMap<>();
        mymap.put(uname, message);

        myRef.child("ServiceRequests").child(serviceRequestName).updateChildren(mymap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError != null) {

                    Log.d("CHAT_LOG", databaseError.getMessage().toString());

                }

            }
        });

        Toast.makeText(this, "Request successfully submitted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PlaceServiceRequest.this, UserHomeActivity.class);
        startActivity(intent);

    }
}
