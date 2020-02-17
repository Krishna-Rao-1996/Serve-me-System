package com.example.servemesystem;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceProviderRegistrationActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_registration);

        //Service Provider Types

        final RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference usersRef = rootref.child("Service_Provider_Types");

        Log.d("Service_Providers", "usersRef= " + usersRef);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Log.d("Service_Providers", "count= " + count);
                String[] str = new String[((int) count)];
                int j = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String name = ds.getKey();

                    str[j] = name;

                    j++;
                }
                int i=0;
                while (i<count)
                {
                    CheckBox checkBox = new CheckBox(getBaseContext());
                    radioGroup.addView(checkBox);
                    ((CheckBox) radioGroup.getChildAt(i)).setText(str[i]);
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
        usersRef.removeEventListener(valueEventListener);


        //Working day Types

        final RadioGroup workingradioGroup = (RadioGroup)findViewById(R.id.workingradioGroup);

        DatabaseReference rootrefer = FirebaseDatabase.getInstance().getReference();

        DatabaseReference usersRefer = rootrefer.child("Working_Day_Types");

        Log.d("Working_Day_Types", "usersRefer= " + usersRefer);

        ValueEventListener workdaylistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                long count = dataSnapshot.getChildrenCount();
                Log.d("Service_Providers", "count= " + count);
                String[] str = new String[((int) count)];
                int j = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String name = ds.getKey();

                    str[j] = name;

                    j++;
                }
                int i=0;
                while (i<count)
                {
                    CheckBox checkBox = new CheckBox(getBaseContext());
                    workingradioGroup.addView(checkBox);
                    ((CheckBox) workingradioGroup.getChildAt(i)).setText(str[i]);
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        usersRefer.addListenerForSingleValueEvent(workdaylistener);
    }
 }

