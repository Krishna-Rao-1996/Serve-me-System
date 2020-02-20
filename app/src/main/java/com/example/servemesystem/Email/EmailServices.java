package com.example.servemesystem.Email;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailServices extends AsyncTask<Void,Void,Void>  {

    //Add those line in dependencies
    //implementation files('libs/activation.jar')
    //implementation files('libs/additionnal.jar')
    //implementation files('libs/mail.jar')

    //Need INTERNET permission

    //Variables
    private Context mContext;

    private String mEmail;
    private String mMessage;
    public FirebaseDatabase database;

    private ProgressBar mProgressDialog;

    public static final String EMAIL = "advancedsegroup4@gmail.com";
    public static final String PASSWORD = "Advancedse_2020";
    public static final String SUBJECT = "ServeMeSystem Password Recovery..!";
    public static final String MESSAGE ="Please enter the below code to reset your password..!";

    //Constructor
    public EmailServices(Context context, String mEmail) {
        this.mEmail = mEmail;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        //mProgressDialog = ProgressBar.show(mContext,"Sending message", "Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismiss progress dialog when message successfully send
        //mProgressDialog.dismiss();

        //Show success toast
        Toast.makeText(mContext,"Message Sent",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Creating a new session
        Session mSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });

        //Creating a DB instance to save the recoveryPin
        database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference("/Users/");


        //GET THE USERNAME FROM THE DB OF THIS PARTICULAR USER AND USE THAT AS THE CHILD.
        // CREATE A NEW CHILD "RECOVERYPIN" AND SAVE THIS PIN VALUE THERE USING SETVALUE
        // https://www.techotopia.com/index.php/Writing_Firebase_Realtime_Database_Data
//        dbRef.child("Users").child(userName).updateChildren(mymap, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//
//                if (databaseError != null) {
//
//                    Log.d("CHAT_LOG", databaseError.getMessage().toString());
//
//                }
//
//            }
//        });

        try {
            int recoveryPin = (int)(Math.random()*9000)+1000;
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(mSession);

            //Setting sender address
            mm.setFrom(new InternetAddress(EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            //Adding subject
            mm.setSubject(SUBJECT);
            //Adding message
            mm.setText(MESSAGE +"\n" + recoveryPin);
            //Sending email
            Transport.send(mm);



        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}