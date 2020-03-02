package com.example.servemesystem.adminScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.servemesystem.R;

public class AdminServiceProviderDetails extends AppCompatActivity {

    TextView textView1,textView2, textView3, textView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_service_provider_details_activity);

        textView1 = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView6);
        textView3 = findViewById(R.id.textView7);
        textView4 = findViewById(R.id.textView7);

        textView1.setText(getIntent().getStringExtra("c1"));
        textView2.setText(getIntent().getStringExtra("c2"));
        textView3.setText(getIntent().getStringExtra("c3"));
        textView4.setText(getIntent().getStringExtra("c4"));
    }
}
