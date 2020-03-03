package com.example.servemesystem.adminScreen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminActivity extends Activity  {
    OperaterDataView adapter;
    RecyclerView recyclerView;
    ArrayList<ServiceProvider> listOfServiceProviders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

// data to populate the RecyclerView with


        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvServiceProvider);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference adminFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");
        adminFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long count = dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ServiceProvider sp = new ServiceProvider();
                    sp.setCompanyname(ds.child("Companyname").getValue().toString());
                    sp.setOfficenumber(ds.child("Officenumber").getValue().toString());
                    sp.setServieTypes(ds.child("ServiceTypes").getValue().toString());
                    listOfServiceProviders.add(sp);
                }
                adapter = new OperaterDataView(getApplicationContext(), listOfServiceProviders);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
