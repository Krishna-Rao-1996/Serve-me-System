package com.example.servemesystem.adminScreen;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingAuthorizationFragment extends Fragment {

    PendingAuthorizationAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ServiceProvider> listOfServiceProviders;
    public PendingAuthorizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.service_provider_pending_authorization_fragment, container, false);
        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.rvPendingAuthorizationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DatabaseReference adminFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");
        adminFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfServiceProviders = new ArrayList<ServiceProvider>();
                Long count = dataSnapshot.getChildrenCount();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(!ds.child("IsVerified").getValue().toString().equalsIgnoreCase("true")){
                        ServiceProvider sp = new ServiceProvider();
                        sp.setVerified(ds.child("IsVerified").getValue().toString());
                        sp.setUserName(ds.getKey().toString());
                        sp.setCompanyname(ds.child("Companyname").getValue().toString());
                        sp.setOfficenumber(ds.child("Officenumber").getValue().toString());
                        //sp.setServieTypes(ds.child("ServiceTypes").getValue().toString());
                        sp.setCity(ds.child("City").getValue().toString());
                        sp.setState(ds.child("State").getValue().toString());
                        if(ds.child("dp").exists()){
                            sp.setDp(ds.child("dp").getValue().toString());
                        }
                        listOfServiceProviders.add(sp);
                    }
                    ServiceProvider sp = new ServiceProvider();
                }
                adapter = new PendingAuthorizationAdapter(getActivity().getApplicationContext(), listOfServiceProviders);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}
