package com.example.servemesystem.Homepage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servemesystem.PlaceServiceRequest;
import com.example.servemesystem.R;
import com.example.servemesystem.ViewServiceRequest;

import java.util.ArrayList;

public class UserHomeActivityAdapter extends RecyclerView.Adapter<UserHomeActivityAdapter.ViewHolder> {
    private String TAG = "UserHomeActivityAdapter";
    private ArrayList<String> serviceType;
    private ArrayList<String> userName;
    private ArrayList<String> serviceDescription;
    private Context mContext;
    public UserHomeActivityAdapter(Context mContext, ArrayList<String> mUserNames, ArrayList<String> serviceType, ArrayList<String> serviceDescription) {
        Log.i(TAG, "Constructor: User Home Adapter called.");

        this.userName = mUserNames;
        this.serviceType = serviceType;
        this.serviceDescription = serviceDescription;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: User Home Adapter called.");
        return serviceType.size();
    }

    @NonNull
    @Override
    public UserHomeActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_home_activity_layout, parent, false);
        UserHomeActivityAdapter.ViewHolder holder = new UserHomeActivityAdapter.ViewHolder(view);
        Log.i(TAG, "onCreateViewHolder: User Home Adapter called.");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: User Home Adapter called.");
        holder.typeName.setText(serviceType.get(position));
        holder.typeDescription.setText(serviceDescription.get(position));
        holder.userHomeScreenParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlaceServiceRequest.class);
//                intent.putExtra("userName", mUserNames.get(position));
                intent.putExtra("serviceRequestName", serviceType.get(position));
//                intent.putExtra("",mUserMessage.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView typeName, typeDescription ;
//        ImageView servicePic;
        LinearLayout userHomeScreenParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            typeName = itemView.findViewById(R.id.serviceProviderTypeName);
            typeDescription = itemView.findViewById(R.id.serviceProviderTypeDescription);
//            servicePic = itemView.findViewById(R.id.servicePic);
            userHomeScreenParent = itemView.findViewById(R.id.userHomeScreenParent);
        }
    }
}
