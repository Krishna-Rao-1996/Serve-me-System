package com.example.servemesystem;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceProviderRegistrationActivity extends UserRegistrationActivity{

    EditText fname,sname1,email1,dob1,address1,city1,phone1,pass1,conpass1,companyname1,Office_Number1,Office_Address1,workinghours;
    Spinner state1;
    DatabaseReference myReg;
    DatabaseReference emailRef;
    DatabaseReference numberref;
    String Fname,Fname1,Email1,Dob1,Address1,State,City1,Phone1,Pass1,Conpass1,Companyname,Officenumber,Office_Address,Workinghours,Servtype="",Worktype="";
    boolean Check = false;
    int count=0,count1=0;
    CheckBox workdaycheckBox;
    CheckBox servicetypecheckBox;
    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
    DatabaseReference rootrefer = FirebaseDatabase.getInstance().getReference().child("Working_Day_Types");
    ArrayList<String> Servicearray = new ArrayList<String>();
    ArrayList<String> Workdayarray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Default methods

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_registration);

        //Service Provider Types
        final RadioGroup serviceradioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        Log.d("Service_Providers", "usersRef= " + rootref);

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
                    servicetypecheckBox = new CheckBox(getBaseContext());
                    serviceradioGroup.addView(servicetypecheckBox);
                    ((CheckBox) serviceradioGroup.getChildAt(i)).setText(str[i]);
                    servicetypecheckBox.setOnClickListener(ServiceType1(servicetypecheckBox));
                    i++;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        rootref.addListenerForSingleValueEvent(valueEventListener);
        rootref.removeEventListener(valueEventListener);


        //Working day Types

        final RadioGroup workingradioGroup = (RadioGroup)findViewById(R.id.workingradioGroup);

        Log.d("Working_Day_Types", "usersRefer= " + rootrefer);

        final ValueEventListener workdaylistener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                long count1 = dataSnapshot.getChildrenCount();
                Log.d("Service_Providers", "count= " + count1);
                String[] str = new String[((int) count1)];
                int j = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String name = ds.getKey();

                    str[j] = name;

                    j++;
                }
                int i=0;
                while (i<count1)
                {
                    workdaycheckBox = new CheckBox(getBaseContext());
                    workingradioGroup.addView(workdaycheckBox);
                    ((CheckBox) workingradioGroup.getChildAt(i)).setText(str[i]);
                    workdaycheckBox.setOnClickListener(ServiceType2(workdaycheckBox));
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        rootrefer.addListenerForSingleValueEvent(workdaylistener);
        rootrefer.removeEventListener(workdaylistener);


        //Registration Button Onclick Event

        Button Registration =  (Button) findViewById(R.id.userReg);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Full Name
                fname = findViewById(R.id.fname);

                Fname = (String) fname.getText().toString();

                boolean allLetters = Fname.matches("[a-zA-Z\\s\'\"]+");

                Log.d("allLetters", "count= " + allLetters);


                if (allLetters != true) {
                    fname.setError("Please Enter a Valid Fullname");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                if (Fname.matches("")) {
                    fname.setError("Fullname field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //User Name
                sname1 = findViewById(R.id.sname1);

                Fname1 = (String) sname1.getText().toString();

                boolean usernamecheck = Fname1.matches("[a-zA-Z.? ]*");

                if(usernamecheck !=true)
                {
                    sname1.setError("Username field cannot have special characters");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                if (Fname1.matches(""))
                {
                    sname1.setError("Username field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("Service_Providers").child(Fname1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            sname1.setError("Username already exists!");
                            Check = false;
                            return;
                        }
                        else
                        {
                            Check = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //Email

                email1 = findViewById(R.id.email1);

                Email1 = (String) email1.getText().toString();

                emailRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");

                emailRef.orderByChild("Email").equalTo(Email1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {
                            email1.setError("Email already exists!");
                            Check = false;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (Email1.matches("")) {
                    email1.setError("Email field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                boolean emailcheck = verifyEmail(Email1);

                if (emailcheck == false) {
                    email1.setError("Email entered is invalid");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Date of Birth
                dob1 = findViewById(R.id.dob1);

                Dob1 = (String) dob1.getText().toString();

                if (Dob1.matches("")) {
                    dob1.setError("Date of Birth field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                boolean isDate = verifydob(Dob1);

                if (isDate != true) {
                    dob1.setError("Date of Birth format is not according to MM/DD/YYYY");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }


                //Address
                address1 = findViewById(R.id.address1);

                Address1 = (String) address1.getText().toString();

                Log.d("Address1", "count= " + Address1);

                if (Address1.matches("")) {
                    address1.setError("Address field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //State
                state1 = (Spinner) findViewById(R.id.state1);

                State = (String) state1.getSelectedItem();

                Log.d("State", "count= " + State);

                if (State.matches("Select a State")) {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a State", Toast.LENGTH_SHORT).show();
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //City

                city1 = findViewById(R.id.city1);

                City1 = (String) city1.getText().toString();

                if (City1.matches("")) {
                    city1.setError("City field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Phone number
                phone1 = findViewById(R.id.phone1);

                Phone1 = (String) phone1.getText().toString();

                numberref = FirebaseDatabase.getInstance().getReference().child("Service_Providers");

                numberref.orderByChild("Phone").equalTo(Phone1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {
                            phone1.setError("Phone number already exists!");
                            Check = false;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (Phone1.matches("")) {
                    phone1.setError("Phone number field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                boolean phonecheck = verifyPhone(Phone1);

                if (phonecheck == false) {
                    phone1.setError("Invalid Phone number");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Pass1

                pass1 = findViewById(R.id.pass1);

                Pass1 = (String) pass1.getText().toString();

                if (Pass1.matches("")) {
                    pass1.setError("Password field cannot be empty");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Confirmpass

                conpass1 = findViewById(R.id.conpass1);

                Conpass1 = (String) conpass1.getText().toString();

                if (Conpass1.matches("")) {
                    conpass1.setError("Password field cannot be empty");
                    Check = false;
                    return;

                }
                else
                {
                    Check = true;
                }

                //Check wheter they are different

                boolean confirmpass = Pass1.matches(Conpass1);

                if (confirmpass == false) {
                    pass1.setError("Please Enter Similar Passwords");
                    conpass1.setError("Please Enter Similar Passwords");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Company name


                companyname1 = findViewById(R.id.companyname1);

                Companyname = (String) companyname1.getText().toString();

                boolean companycheck = Companyname.matches("[a-zA-Z\\s\'\"]+");

                if (Companyname.matches("")) {
                    companyname1.setError("Company name field cannot be empty");
                    Check = false;
                    return;

                }
                else
                {
                    Check = true;
                }

                if(companycheck == false)
                {
                    companyname1.setError("Company name entered is not valid");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Office Number

                Office_Number1 = findViewById(R.id.Office_Number1);

                Officenumber = (String) Office_Number1.getText().toString();

                boolean Officenumbercheck = verifyPhone(Officenumber);

                if (Officenumber.matches("")) {
                    Office_Number1.setError("Office number field cannot be empty");
                    Check = false;
                    return;

                }
                else
                {
                    Check = true;
                }

                if(Officenumbercheck == false)
                {
                    Office_Number1.setError("Office number entered is not valid");
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                //Office Address

                Office_Address1 = findViewById(R.id.Office_Address1);

                Office_Address = (String) Office_Address1.getText().toString();

                if (Office_Address.matches("")) {
                    Office_Address1.setError("Office Address field cannot be empty");
                    Check = false;
                    return;

                }
                else
                {
                    Check = true;
                }


                //Working hours

                workinghours = findViewById(R.id.workinghours);

                Workinghours = (String) workinghours.getText().toString();

                if (Workinghours.matches("")) {
                    workinghours.setError("Working hours field cannot be empty");
                    Check = false;
                    return;

                }
                else
                {
                    Check = true;
                }


                //Service type and Workday Validation

                if(Servicearray == null)
                {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a Service Type", Toast.LENGTH_SHORT).show();
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }

                if(Workdayarray == null)
                {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a Working Day", Toast.LENGTH_SHORT).show();
                    Check = false;
                    return;
                }
                else
                {
                    Check = true;
                }


                Log.d("Check", "Check= " + Check);

                //Sending Data to Firebase
                for (int i = 0; i < Servicearray.size(); i++)
                {
                    Servtype += Servicearray.get(i) + ",";
                }
                Log.d("Servtype", "Check= " + Servtype);
                for (int i = 0; i < Workdayarray.size(); i++)
                {
                    Worktype += Workdayarray.get(i) + ",";
                }
                Log.d("worktype", "Check= " + Worktype);
                myReg = database.getReference("Service_Providers");
                if(Check == true)
                {
                    Map mymap = new HashMap<>();
                    mymap.put("FirstName",Fname);
                    mymap.put("Phone",Phone1);
                    mymap.put("DateOfBirth",Dob1);
                    mymap.put("Email",Email1);
                    mymap.put("City",City1);
                    mymap.put("State",State);
                    mymap.put("Password",Pass1);
                    mymap.put("Companyname",Companyname);
                    mymap.put("Officenumber",Officenumber);
                    mymap.put("Officeaddress",Office_Address);
                    mymap.put("Workinghours",Workinghours);
                    mymap.put("ServiceTypes",Servtype);
                    mymap.put("Workingdays",Worktype);
                    mymap.put("IsVerified",false);
                    count++;
                    myReg.child(Fname1).updateChildren(mymap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());

                            }

                        }
                    });

                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter the above mentioned details",Toast.LENGTH_SHORT).show();
                }


            }

        });
    }
    View.OnClickListener ServiceType1(final Button button)
    {
       return  new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               Log.d("Button clicked is", "count= " + button.getText());

               if(Servicearray.contains(button.getText()))
               {
                   Servicearray.remove(button.getText().toString());
               }
               else
               {
                   Servicearray.add(button.getText().toString());
               }

               Log.d("Servicearray", "count= " + Servicearray);
           }
       };
    }
    View.OnClickListener ServiceType2(final Button button)
    {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("Button clicked is", "count= " + button.getText());

                if(Workdayarray.contains(button.getText()))
                {
                    Workdayarray.remove(button.getText().toString());
                }
                else
                {
                    Workdayarray.add(button.getText().toString());
                }

                Log.d("Servicearray", "count= " + Workdayarray);
            }
        };
    }
 }

