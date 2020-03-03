package com.example.servemesystem;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ServiceProviderRegistrationActivity extends RegistrationHelper{

    EditText fNameET,uNameET,emailAdressET,dateOfbirthET,homeAddressET,cityET,phoneNumberET,passwordET,confirmPasswordET,
            companyNameET, officeNumberET,officeAddressET,officeCityET,zipCodeET;
    Spinner stateSpinner;
    DatabaseReference myReg;
    DatabaseReference addingReg;
    DatabaseReference emailRef;
    DatabaseReference numberRef;
    String fNameString,uNameString,emailAddressString,dateOfbirthETString,homeAddressString,stateSpinnerString,cityString,phoneNumberString
            ,passwordString,confirmPasswordString,companyNameString,officeNumberString,officeAddressString,zipCodeString,officeCityString
            ,Servtype = "",ServiceArr = "";
    boolean Check = true;
    CheckBox servicetypecheckBox;
    DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
    ArrayList<String> Servicearray = new ArrayList<String>();
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
                    String name = ds.getKey().toString();

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


        //Registration Button Onclick Event

        Button Registration =  (Button) findViewById(R.id.userReg);

        Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Full Name
                fNameET = findViewById(R.id.fname);

                fNameString =  fNameET.getText().toString();

                boolean allLetters = fNameString.matches("[a-zA-Z\\s\'\"]+");

                Log.d("allLetters", "count= " + allLetters);


                if (allLetters != true) {
                    fNameET.setError("Please Enter a Valid Fullname");
                    Check = false;
                }

                if (fNameString.matches("")) {
                    fNameET.setError("Fullname field cannot be empty");
                    Check = false;
                }

                //User Name
                uNameET = findViewById(R.id.sname1);

                uNameString = uNameET.getText().toString();

                if(!verifyUsername(uNameString))
                {
                    uNameET.setError("Username field cannot have special characters");
                    Check = false;
                }

                if (uNameString.matches(""))
                {
                    uNameET.setError("Username field cannot be empty");
                    Check = false;
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("Service_Providers").child(uNameString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            uNameET.setError("Username already exists!");
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

                emailAdressET = findViewById(R.id.email1);

                emailAddressString = emailAdressET.getText().toString();

                emailRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");

                emailRef.orderByChild("Email").equalTo(emailAddressString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {
                            emailAdressET.setError("Email already exists!");
                            Check = false;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (emailAddressString.matches("")) {
                    emailAdressET.setError("Email field cannot be empty");
                    Check = false;
                }

                if (!verifyEmail(emailAddressString)) {
                    emailAdressET.setError("Email entered is invalid");
                    Check = false;
                }

                //Date of Birth
                dateOfbirthET = findViewById(R.id.dob1);

                dateOfbirthETString = dateOfbirthET.getText().toString();

                if (dateOfbirthETString.matches("")) {
                    dateOfbirthET.setError("Date of Birth field cannot be empty");
                    Check = false;
                }

                if (!verifydob(dateOfbirthETString)) {
                    dateOfbirthET.setError("Date of Birth format is not according to MM/DD/YYYY");
                    Check = false;
                }

                //Address
                homeAddressET = findViewById(R.id.address1);

                homeAddressString = homeAddressET.getText().toString();

                Log.d("Address1", "count= " + homeAddressString);

                if (homeAddressString.matches("")) {
                    homeAddressET.setError("Address field cannot be empty");
                    Check = false;
                }

                //State
                stateSpinner = (Spinner) findViewById(R.id.state1);

                stateSpinnerString = (String) stateSpinner.getSelectedItem();

                Log.d("State", "count= " + stateSpinnerString);

                if (stateSpinnerString.matches("Select a State")) {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a State", Toast.LENGTH_SHORT).show();
                    Check = false;
                }

                //City

                cityET = findViewById(R.id.city1);

                cityString =  cityET.getText().toString();

                if (cityString.matches("")) {
                    cityET.setError("City field cannot be empty");
                    Check = false;
                }

                //Phone number
                phoneNumberET = findViewById(R.id.phone1);

                phoneNumberString = phoneNumberET.getText().toString();

                numberRef = FirebaseDatabase.getInstance().getReference().child("Service_Providers");

                numberRef.orderByChild("Phone").equalTo(phoneNumberString).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {
                            phoneNumberET.setError("Phone number already exists!");
                            Check = false;
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if (phoneNumberString.matches("")) {
                    phoneNumberET.setError("Phone number field cannot be empty");
                    Check = false;
                }
                if (!verifyPhone(phoneNumberString)) {
                    phoneNumberET.setError("Invalid Phone number");
                    Check = false;
                }

                //Pass1
                passwordET = findViewById(R.id.pass1);

                passwordString =  passwordET.getText().toString();

                if (passwordString.matches("")) {
                    passwordET.setError("Password field cannot be empty");
                    Check = false;
                }

                //Confirmpass

                confirmPasswordET = findViewById(R.id.conpass1);

                confirmPasswordString = confirmPasswordET.getText().toString();

                if (confirmPasswordString.matches("")) {
                    confirmPasswordET.setError("Password field cannot be empty");
                    Check = false;

                }


                if (!passwordString.matches(confirmPasswordString)) {
                    passwordET.setError("Please Enter Similar Passwords");
                    confirmPasswordET.setError("Please Enter Similar Passwords");
                    Check = false;
                }

                //Company name


                companyNameET = findViewById(R.id.companyname1);

                companyNameString = companyNameET.getText().toString();

                if (companyNameString.matches("")) {
                    companyNameET.setError("Company name field cannot be empty");
                    Check = false;
                }

                if(!companyNameString.matches("[a-zA-Z\\s\'\"]+"))
                {
                    companyNameET.setError("Company name entered is not valid");
                    Check = false;
                }

                //Office Number

                officeNumberET = findViewById(R.id.Office_Number1);

                officeNumberString = officeNumberET.getText().toString();

                if (officeNumberString.matches("")) {
                    officeNumberET.setError("Office number field cannot be empty");
                    Check = false;
                }

                if(!verifyPhone(officeNumberString))
                {
                    officeNumberET.setError("Office number entered is not valid");
                    Check = false;
                }

                //Office Address

                officeAddressET = findViewById(R.id.Office_Address1);

                officeAddressString = officeAddressET.getText().toString();

                if (officeAddressString.matches("")) {
                    officeAddressET.setError("Office Address field cannot be empty");
                    Check = false;
                }


                zipCodeET = findViewById(R.id.zipCode);

                zipCodeString = zipCodeET.getText().toString();

                if(zipCodeString.matches(""))
                {
                    zipCodeET.setError("Zipcode field cannot be empty");
                    Check = false;
                }

                officeCityET = findViewById(R.id.Office_City1);

                officeCityString = officeCityET.getText().toString();

                if(officeCityString.matches(""))
                {
                    officeCityET.setError("Office city field cannot be empty");
                    Check = false;
                }


                //Service type and Workday Validation

                if(Servicearray == null)
                {
                    Toast.makeText(ServiceProviderRegistrationActivity.this, "Please select a Service Type", Toast.LENGTH_SHORT).show();
                    Check = false;
                }

                Log.d("Check", "Check= " + Check);

                //Sending Data to Firebase
                for (int i = 0; i < Servicearray.size(); i++)
                {
                    if(!Servtype.contains(Servicearray.get(i))) {

                        Servtype += Servicearray.get(i) + ",";
                    }
                }

                Log.d("Servtype", "Check= " + Servtype);
                myReg = database.getReference("Service_Providers");
                addingReg = database.getReference("Service_Provider_Credentials");
                if(Check)
                {
                    Map mymap = new HashMap<>();
                    mymap.put("FullName",fNameString);
                    mymap.put("Phone",phoneNumberString);
                    mymap.put("DateOfBirth",dateOfbirthETString);
                    mymap.put("Email",emailAddressString);
                    mymap.put("City",cityString);
                    mymap.put("State",stateSpinnerString);
                    mymap.put("Password",passwordString);
                    mymap.put("Zipcode",zipCodeString);
                    mymap.put("dp","");
                    mymap.put("Companyname",companyNameString);
                    mymap.put("Officenumber",officeNumberString);
                    mymap.put("Officeaddress",officeAddressString);
                    mymap.put("Officecity",officeCityString);
                    mymap.put("ServiceTypes",Servtype);
                    mymap.put("IsVerified","false");
                    myReg.child(uNameString).updateChildren(mymap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());

                            }

                        }
                    });

                    Map userCred = new HashMap<>();
                    userCred.put(uNameString, passwordString);
                    addingReg.updateChildren(userCred, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if (databaseError != null) {

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());

                            }

                        }
                    });



                    Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("currUser", MODE_PRIVATE).edit();
                    editor.putString("userName", uNameString);
                    editor.putString("type","serviceprovider");
                    editor.apply();
                    Intent login = new Intent(ServiceProviderRegistrationActivity.this,LoginActivity.class);
                    startActivity(login);
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

    @Override
    protected void sendData() {

    }
}

