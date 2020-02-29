package com.example.servemesystem.Homepage;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servemesystem.R;
import com.example.servemesystem.ViewServiceRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder>{
    private static final String TAG = "ServiceProviderAdapter";
    private ArrayList<String> mUserNames = new ArrayList<>();
    private ArrayList<String> mRequestTypes = new ArrayList<>();
    private ArrayList<String> mUserMessage = new ArrayList<>();
    private Context mContext;
    public ServiceProviderAdapter(Context mContext,ArrayList<String> mUserNames, ArrayList<String> mRequestTypes, ArrayList<String> mUserMessage) {
        this.mUserNames = mUserNames;
        this.mRequestTypes = mRequestTypes;
        this.mUserMessage=mUserMessage;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_requests, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.userName.setText(mUserNames.get(position));
        holder.requestType.setText(mRequestTypes.get(position));
        Image_Resource image_resource = new Image_Resource();
        if(mRequestTypes.get(position).equals("Plumbing")){
            Picasso.get().load(image_resource.jacuzziImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Electrical")){
            Picasso.get().load(image_resource.electricalImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Appliances")){
            Picasso.get().load(image_resource.applianceImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Computer Repair")){
            Picasso.get().load(image_resource.computerRepairImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Home Cleaning")){
            Picasso.get().load(image_resource.homeCleaningImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Home repair and Painting")){
            Picasso.get().load(image_resource.homeRepairImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Packaging and Moving")){
            Picasso.get().load(image_resource.packingImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Pest Control")){
            Picasso.get().load(image_resource.pestControlImg).into(holder.servicePic);
        }
        else if(mRequestTypes.get(position).equals("Tutoring")){
            Picasso.get().load(image_resource.tutoringImg).into(holder.servicePic);
        }
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewServiceRequest.class);
                intent.putExtra("userName", mUserNames.get(position));
                intent.putExtra("serviceReq", mRequestTypes.get(position));
                intent.putExtra("userMessage",mUserMessage.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUserNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName,requestType;
        ImageView servicePic;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            requestType=itemView.findViewById(R.id.request);
            servicePic=itemView.findViewById(R.id.servicePic);
            parentLayout=itemView.findViewById(R.id.parentLayout);
        }
    }
}
