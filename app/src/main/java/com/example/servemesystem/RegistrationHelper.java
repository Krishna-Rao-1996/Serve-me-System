package com.example.servemesystem;


import android.app.Activity;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public abstract class RegistrationHelper extends Activity {
    String password,fullName,userName,email,phone,city,state,country,dateOfBirth,confirmPass;
    public boolean verifyName(String name){
        for(int i=0;i<name.length();i++){
            if(Character.isAlphabetic(name.charAt((i)))){
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    public boolean verifyEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();

    }

    public boolean verifydob(String dateOfBirth){
        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/dd/yyyy");
        sdfrmt.setLenient(false);
        try {
            sdfrmt.parse(dateOfBirth);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    public boolean verifyPhone(String phone){
        if(phone.length()!=10){
            return false;
        }
        for(int i=0;i<phone.length();i++){
            if(Integer.parseInt(String.valueOf(phone.charAt(i)))>=0 ||Integer.parseInt(String.valueOf(phone.charAt(i)))<=9 ){
                continue;
            }
            else {
                return false;
            }
        }
        return true;

    }
    public boolean verifyConfirmPass(String confirmPass,String password){
        if((this.password).equals(this.confirmPass)){
            return true;
        }
        return false;
    }
    abstract void sendData();
    abstract void fetchData();
    public String getPassword(EditText editText){
        this.password=editText.getText().toString();
        return  this.password;
    }
    public String getFullName(EditText editText){
        this.fullName=editText.getText().toString();
        return  this.fullName;
    }
    public String getUserName(EditText editText){
        this.userName=editText.getText().toString();
        return  this.userName;
    }
    public String getEmail(EditText editText){
        this.email=editText.getText().toString();
        return  this.email;
    }
    public String getPhone(EditText editText){
        this.phone=editText.getText().toString();
        return  this.phone;
    }
    public String getCity(EditText editText){
        this.city=editText.getText().toString();
        return  this.city;
    }
    public String getState(EditText editText){
        this.state=editText.getText().toString();
        return  this.state;
    }
    public String getCountry(EditText editText){
        this.country=editText.getText().toString();
        return  this.country;
    }
    public String getDateOfBirth(EditText editText){
        this.dateOfBirth=editText.getText().toString();
        return  this.dateOfBirth;
    }
    public String getConfirmPass(EditText editText){
        this.confirmPass=editText.getText().toString();
        return  this.confirmPass;
    }
}
