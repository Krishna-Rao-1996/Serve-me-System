package com.example.servemesystem.adminScreen;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.servemesystem.LoginActivity;
import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceCategory;
import com.example.servemesystem.domain.ServiceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.Context;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceCategoryFragment extends Fragment {
    private Activity activity;

    public Context getContext() {
        return activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }
    private ServiceCategoryAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<ServiceCategory> listOfServiceCategory;
    Button addServiceCategoryBtn;

    public ServiceCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.service_category_fragment, container, false);
        // set up the RecyclerView
        recyclerView = view.findViewById(R.id.rvServiceCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DatabaseReference adminFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
        adminFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfServiceCategory = new ArrayList<ServiceCategory>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    ServiceCategory sc=new ServiceCategory();
                    sc.setServiceCategoryName(ds.getKey());
                    sc.setServiceCategoryDescription(ds.getValue().toString());
                    listOfServiceCategory.add(sc);
                }
                adapter = new ServiceCategoryAdapter(getActivity().getApplicationContext(), listOfServiceCategory);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        addServiceCategoryBtn = (Button) getActivity().findViewById(R.id.addServiceCategoryBtn);
        addServiceCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// get prompts.xml view
                LayoutInflater li = LayoutInflater.from(activity);
                View promptsView = li.inflate(R.layout.service_category_add_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        activity);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText serviceCategoryInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogServiceCategoryInput);
                final EditText descriptionInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogDescriptionInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("ADD",null)
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });


                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(serviceCategoryInput.getText().toString().length()==0){
                            descriptionInput.setError(null, null);
                            serviceCategoryInput.setError("Service Category is empty!");
                            serviceCategoryInput.requestFocus();
                        }
                        else if(descriptionInput.getText().toString().length()==0){
                            serviceCategoryInput.setError(null, null);
                            descriptionInput.setError("Description is empty!");
                            descriptionInput.requestFocus();
                        }
                        else {
                            String emailRegex = "^[A-Za-z0-9]+$";

                            Pattern pat = Pattern.compile(emailRegex);

                            if(!pat.matcher(serviceCategoryInput.getText().toString()).matches()){
                                descriptionInput.setError(null, null);
                                serviceCategoryInput.setError("Service Category can’t have special characters!");
                                serviceCategoryInput.requestFocus();
                            }
                            else{
                                DatabaseReference adminFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
                                adminFirebaseRef.child(serviceCategoryInput.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue()!=null){
                                            descriptionInput.setError(null, null);
                                            serviceCategoryInput.setError("Service Category already exists!");
                                            serviceCategoryInput.requestFocus();
                                        }
                                        else{
                                            DatabaseReference serviceCategoryFirebaseRef=FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
                                            Map serviceCategory = new HashMap<>();
                                            serviceCategory.put(serviceCategoryInput.getText().toString(), descriptionInput.getText().toString());
                                            serviceCategoryFirebaseRef.updateChildren(serviceCategory, new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                    if (databaseError != null) {
                                                        Log.d("CHAT_LOG", databaseError.getMessage().toString());
                                                    }
                                                }
                                            });
                                            Toast.makeText(getActivity(), "Add Service Category Successful", Toast.LENGTH_LONG).show();
                                            alertDialog.dismiss();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    }
                });

            }
        });

    }



}
