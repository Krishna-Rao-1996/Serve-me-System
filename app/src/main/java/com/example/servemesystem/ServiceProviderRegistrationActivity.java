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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class ServiceProviderRegistrationActivity extends UserRegistrationActivity{

    EditText fname,sname1,email1,dob1,address1,city1,phone1,pass1,conpass1,companyname1,Office_Number1,Office_Address1,workinghours;
    Spinner state1;
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


        //Registration Button Onclick Event

        Button Registration =  (Button) findViewById(R.id.userReg);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Full Name
                fname = findViewById(R.id.fname);

                String Fname = (String) fname.getText().toString();

                boolean allLetters = verifyName(Fname);

                Log.d("allLetters", "count= " + allLetters);


                if (allLetters != true) {
                    fname.setError("Please Enter a Valid Fullname");
                    return;
                }

                if (Fname.matches("")) {
                    fname.setError("Fullname field cannot be empty");
                    return;
                }

                //User Name
                sname1 = findViewById(R.id.sname1);

                String Fname1 = (String) sname1.getText().toString();

                if (Fname1.matches("")) {
                    sname1.setError("Username field cannot be empty");
                    return;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("Service_Providers").child(Fname1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            sname1.setError("Username already exists!");
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Email

                email1 = findViewById(R.id.email1);

                String Email1 = (String) email1.getText().toString();

                if (Email1.matches("")) {
                    email1.setError("Email field cannot be empty");
                    return;
                }

                boolean emailcheck = verifyEmail(Email1);

                if (emailcheck == false) {
                    email1.setError("Email entered is invalid");
                    return;
                }

                //Date of Birth
                dob1 = findViewById(R.id.dob1);

                String Dob1 = (String) dob1.getText().toString();

                if (Dob1.matches("")) {
                    dob1.setError("Date of Birth field cannot be empty");
                    return;
                }

                boolean isDate = verifydob(Dob1);

                if (isDate != true) {
                    dob1.setError("Date of Birth format is not according to MM/DD/YYYY");
                    return;
                }


                //Address
                address1 = findViewById(R.id.address1);

                String Address1 = (String) address1.getText().toString();

                Log.d("Address1", "count= " + Address1);

                if (Address1.matches("")) {
                    address1.setError("Address field cannot be empty");
                    return;
                }

                //State
                state1 = (Spinner) findViewById(R.id.state1);

                String State = (String) state1.getSelectedItem();

                Log.d("State", "count= " + State);

                if (State.matches("Select a State")) {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a State", Toast.LENGTH_SHORT).show();
                    return;
                }

                //City

                city1 = findViewById(R.id.city1);

                String City1 = (String) city1.getText().toString();

                if (City1.matches("")) {
                    city1.setError("City field cannot be empty");
                    return;
                }

                phone1 = findViewById(R.id.phone1);

                String Phone1 = (String) phone1.getText().toString();

                if (Phone1.matches("")) {
                    phone1.setError("Phone number field cannot be empty");
                    return;
                }

                boolean phonecheck = verifyPhone(Phone1);

                if (phonecheck == false) {
                    phone1.setError("Invalid Phone number");
                    return;
                }

                //Pass1

                pass1 = findViewById(R.id.pass1);

                String Pass1 = (String) pass1.getText().toString();

                if (Pass1.matches("")) {
                    pass1.setError("Password field cannot be empty");
                    return;
                }

                //Confirmpass

                conpass1 = findViewById(R.id.conpass1);

                String Conpass1 = (String) conpass1.getText().toString();

                if (Conpass1.matches("")) {
                    conpass1.setError("Password field cannot be empty");
                    return;

                }

                //Check wheter they are different

                boolean confirmpass = verifyConfirmPass(Pass1,Conpass1);

                if (confirmpass == false) {
                    pass1.setError("Please Enter Similar Passwords");
                    conpass1.setError("Please Enter Similar Passwords");
                    return;
                }
            }
        });
    }
 }

