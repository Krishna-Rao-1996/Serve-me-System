package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    private Button userReg,loginButton,loginButtonForServiceProvider;
    private EditText username,password;
    //private String usernameFromDB = null;
    private String passwordFromDB = null;
    FirebaseAuth uAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase database;
    DatabaseReference myRef;
    private CheckBox showPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        uAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register();
        loginForUser();
        loginForServiceProvider();
        password = findViewById(R.id.password);
        showPassword=findViewById(R.id.showPassword);
        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //If selected, show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //else hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

    }

    private void loginForUser() {
        loginButton = findViewById(R.id.Logbutton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();
            }
        });
    }


    private void loginEvent() {
        if(TextUtils.isEmpty(username.getText())){
            //return username is empty
            password.setError(null,null);
//            username.setError(null, null);
            username.setError("Username is empty!");
            username.requestFocus();

        }
        else if(password.getText().toString().length()==0){
            //return password is empty
            //Toast.makeText(getApplicationContext(), "Password is empty,Please Re-Enter", Toast.LENGTH_SHORT).show();
//          password.setError(null,null);
            username.setError(null, null);
            password.setError("Password is empty!");
            password.requestFocus();
        }
        else {
            queryData();
        }

    }

    private void queryData() {
        myRef.child("User_Credentials").child(username.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getValue());
                System.out.println(dataSnapshot.getValue());
                if(dataSnapshot.getValue()!=null){
                    passwordFromDB = dataSnapshot.getValue().toString();
                }
                if(passwordFromDB == null){
                    //return username is not register,page not jump to homepage
                    //Toast.makeText(getApplicationContext(), "Username is not registered,Please Re-Enter", Toast.LENGTH_SHORT).show();
                    password.setError(null,null);
//                    username.setError(null, null);
                    username.setError("Username is not registered!");
                    username.requestFocus();
                }
                else if(!password.getText().toString().equals(passwordFromDB)){
                    //return password is not correct,page not jump to homepage
                    //Toast.makeText(getApplicationContext(), "Password is not correct,Please Re-Enter", Toast.LENGTH_SHORT).show();
//                    password.setError(null,null);
                    username.setError(null, null);
                    passwordFromDB = null;
                    password.setError("Password is not correct!");
                    password.requestFocus();
                }
                else{
                    //username and password match,return login success and jump to homepage
                    passwordFromDB = null;
                    Intent logInIntent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(logInIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loginForServiceProvider() {
        loginButtonForServiceProvider = findViewById(R.id.LogbuttonForServiceProvider);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        loginButtonForServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEventForServiceProvider();
            }
        });
    }


    private void loginEventForServiceProvider() {
        if(TextUtils.isEmpty(username.getText())){
            //return username is empty
            //Toast.makeText(getApplicationContext(), "Username is empty,Please Re-Enter", Toast.LENGTH_SHORT).show();
            password.setError(null,null);
//            username.setError(null, null);
            username.setError("Username is empty!");
            username.requestFocus();

        }
        else if(password.getText().toString().length()==0){
            //return password is empty
            //Toast.makeText(getApplicationContext(), "Password is empty,Please Re-Enter", Toast.LENGTH_SHORT).show();
            username.setError(null, null);
            password.setError("Password is empty!");
            password.requestFocus();
        }
        else {
            queryDataForServiceProvider();
        }

    }

    private void queryDataForServiceProvider() {
        //We should modify here.
        myRef.child("Service_Provider_Credentials").child(username.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    passwordFromDB = dataSnapshot.getValue().toString();
                }
                if(passwordFromDB == null){
                    //return username is not register,page not jump to homepage
                    //Toast.makeText(getApplicationContext(), "Username is not registered,Please Re-Enter", Toast.LENGTH_SHORT).show();
                    password.setError(null,null);
//                    username.setError(null, null);
                    username.setError("Username is not registered!");
                    username.requestFocus();
                }
                else if(!password.getText().toString().equals(passwordFromDB)){
                    //return password is not correct,page not jump to homepage
                   // Toast.makeText(getApplicationContext(), "Password is not correct,Please Re-Enter", Toast.LENGTH_SHORT).show();
                    username.setError(null, null);
                    passwordFromDB = null;
                    password.setError("Password is not correct!");
                    password.requestFocus();
                }
                else{
                    //username and password match,return login success and jump to homepage
                    passwordFromDB = null;
                    Intent logInIntent = new Intent(LoginActivity.this,ServiceProviderHome.class);
                    startActivity(logInIntent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void register() {
        userReg=findViewById(R.id.regbutton);
        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInt = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(myInt);
            }
        });
    }
}
