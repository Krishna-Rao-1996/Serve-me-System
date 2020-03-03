package com.example.servemesystem.adminScreen;

import com.example.servemesystem.domain.ServiceProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminUpdates {

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    public void approveServiceProvider(String userName){
        myRef.child("Service_Providers").child(userName).child("IsVerified").setValue("true");
    }

    public void rejectServiceProvider(String userName){
        myRef.child("Service_Providers").child(userName).child("IsVerified").setValue("rejected");
    }
}
