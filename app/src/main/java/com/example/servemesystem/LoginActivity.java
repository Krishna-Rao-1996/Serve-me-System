package com.example.servemesystem;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.servemesystem.Homepage.ServiceProviderHomeActivity;
import com.example.servemesystem.Homepage.UpdateProfile;
import com.example.servemesystem.Homepage.UserHomeActivity;
import com.example.servemesystem.adminScreen.AdminMainActivity;
import com.example.servemesystem.domain.ConstantResources;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {
    private RadioGroup rgLogin;
    private Button userReg,loginButton,loginButtonForServiceProvider;
    private EditText username,password;
    private TextView forgotPassword;
    //private String usernameFromDB = null;
    private String passwordFromDB = null;
    FirebaseAuth uAuth;
    private static final String TAG = "EmailPassword";
    FirebaseDatabase database;
    DatabaseReference myRef;
    private CheckBox showPassword;
    String info = "User_Credentials";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uAuth = FirebaseAuth.getInstance();
        rgLogin = findViewById(R.id.rg_1);
        register();
        login();
        forgotPassword();
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
        rgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.customer_check_in:
                        info = ConstantResources.USER_CREDENTIALS;
                        break;
                    case R.id.vender_check_in:
                        info = ConstantResources.SERVICE_PROVIDER_CREDENTIALS;
                        break;
                    case R.id.admin_check_in:
                        info = ConstantResources.ADMINISTRATOR_CREDENTIALS;
                        break;
                }
            }
        });


    }

    private void login() {
        loginButton = findViewById(R.id.logbutton);
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
        myRef.child(info).child(username.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null){
                    passwordFromDB = dataSnapshot.getValue().toString();
                }
                if(passwordFromDB == null){
                    //return username is not register,page not jump to homepage
                    //Toast.makeText(getApplicationContext(), "Username is not registered,Please Re-Enter", Toast.LENGTH_SHORT).show();
                    password.setError(null,null);
//                  username.setError(null, null);
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
                    if(info.equals(ConstantResources.ADMINISTRATOR_CREDENTIALS)){
                        Intent logInIntent = new Intent(LoginActivity.this, AdminMainActivity.class);
                        startActivity(logInIntent);
                    } else if(info.equals(ConstantResources.USER_CREDENTIALS)){
                        SharedPreferences.Editor editor = getSharedPreferences(ConstantResources.SHARED_PREF_CURRENT_SESSION, MODE_PRIVATE).edit();
                        editor.putString(ConstantResources.SHARED_PREF_USERNAME, username.getText().toString());
                        editor.putString(ConstantResources.SHARED_PREF_USER_TYPE, ConstantResources.USER_TYPE_CUSTOMER);
                        editor.apply();
                        Intent logInIntent = new Intent(LoginActivity.this, UserHomeActivity.class);
                        startActivity(logInIntent);
                    }
                    else {
                        SharedPreferences.Editor editor = getSharedPreferences(ConstantResources.SHARED_PREF_CURRENT_SESSION, MODE_PRIVATE).edit();
                        editor.putString(ConstantResources.SHARED_PREF_USERNAME, username.getText().toString());
                        editor.putString(ConstantResources.SHARED_PREF_USER_TYPE, ConstantResources.USER_TYPE_SERVICE_PROVIDER);
                        editor.apply();
                        Intent logInIntent = new Intent(LoginActivity.this, ServiceProviderHomeActivity.class);
                        startActivity(logInIntent);
                    }
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

    private void forgotPassword() {
        forgotPassword = findViewById(R.id.textView2);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInt = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(myInt);
            }
        });
    }
}
