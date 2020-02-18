package com.example.servemesystem;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ServiceProviderRegistrationActivity extends Activity{

    EditText fname,sname1,email1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Default methods

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
        usersRefer.removeEventListener(workdaylistener);

        Button Registration =  (Button) findViewById(R.id.userReg);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Full Name
                fname = findViewById(R.id.fname);

                String Fname = fname.getText().toString();

                boolean allLetters = Fname.matches("[a-zA-Z]*");

                Log.d("allLetters", "count= " + allLetters);


                if(allLetters != true)
                {
                    fname.setError("Please Enter a Valid Fullname");
                    return;
                }

                if (Fname.matches(""))
                {
                    fname.setError("Fullname field cannot be empty");
                    return;
                }

                //User Name
                sname1 = findViewById(R.id.sname1);

                String Fname1 = sname1.getText().toString();

                if (Fname1.matches(""))
                {
                    sname1.setError("Username field cannot be empty");
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("Service_Providers").child(Fname1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            sname1.setError("Username already exists!");
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Email
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(emailRegex);

                email1 = findViewById(R.id.email1);

                String Email1 = email1.getText().toString();

                if (Email1.matches(""))
                {
                    email1.setError("Email field cannot be empty");
                    return;
                }


            }
        });
    }
 }

