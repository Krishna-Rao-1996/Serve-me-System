package com.example.servemesystem.adminScreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servemesystem.AdminMainActivity;
import com.example.servemesystem.Homepage.Image_Resource;
import com.example.servemesystem.MainActivity;
import com.example.servemesystem.PendingAuthServiceProviderDetails;
import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceCategory;
import com.example.servemesystem.domain.ServiceProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    private ArrayList<ServiceCategory> mData;
    private LayoutInflater mInflater;
    private Context context;
    public AdminMainActivity activity;


    public ServiceCategoryAdapter(Context context, ArrayList<ServiceCategory> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        //this.context=context;
    }

    @Override
    public ServiceCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.activity_service_category_adapter, parent, false);
        this.context=parent.getContext();
        return new ServiceCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCategoryAdapter.ViewHolder holder, int position) {
        final ServiceCategory serviceCategory = mData.get(position);
        final String scname=serviceCategory.getServiceCategoryName();
        holder.serviceCategoryName.setText(serviceCategory.getServiceCategoryName());
        holder.serviceCategoryDescription.setText(serviceCategory.getServiceCategoryDescription());
        Image_Resource image_resource = new Image_Resource();
        if(serviceCategory.getServiceCategoryName().equals("Plumbing")){
            Picasso.get().load(image_resource.jacuzziImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Electrical")){
            Picasso.get().load(image_resource.electricalImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Appliances")){
            Picasso.get().load(image_resource.applianceImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Computer Repair")){
            Picasso.get().load(image_resource.computerRepairImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Home cleaning")){
            Picasso.get().load(image_resource.homeCleaningImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Home repair and Painting")){
            Picasso.get().load(image_resource.homeRepairImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Packaging and Moving")){
            Picasso.get().load(image_resource.packingImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Pest Control")){
            Picasso.get().load(image_resource.pestControlImg).into(holder.imageView);
        }
        else if(serviceCategory.getServiceCategoryName().equals("Tutoring")){
            Picasso.get().load(image_resource.tutoringImg).into(holder.imageView);
        }
        else{
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
        holder.serviceCategoryModifyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        holder.serviceCategoryDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Whether to delete "+scname+"?", Toast.LENGTH_LONG).show();
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Service Category Delete")
                        .setMessage("Whether to delete "+scname+"?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseReference adminFirebaseRef = FirebaseDatabase.getInstance().getReference().child("Service_Provider_Types");
                                adminFirebaseRef.child(scname).removeValue();
                                Toast.makeText(context, scname+" has been deleted !", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                })
                        .create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        EditText serviceCategoryName;
        TextView serviceCategoryDescription;
        Button serviceCategoryModifyBtn;
        Button serviceCategoryDeleteBtn;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.serviceCategoryImage);
            serviceCategoryName = itemView.findViewById(R.id.serviceCategoryName);
            serviceCategoryDescription = itemView.findViewById(R.id.serviceCategoryDescription);
            serviceCategoryModifyBtn = itemView.findViewById(R.id.serviceCategoryModifyBtn);
            serviceCategoryDeleteBtn = itemView.findViewById(R.id.serviceCategoryDeleteBtn);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), PendingAuthServiceProviderDetails.class);
//            intent.putExtra("c1",((EditText)(itemView.findViewById(R.id.serviceCategoryName))).getText().toString());
//            intent.putExtra("c2",((TextView)(itemView.findViewById(R.id.serviceCategoryDescription))).getText().toString());
//            view.getContext().startActivity(intent);
        }

    }


}
