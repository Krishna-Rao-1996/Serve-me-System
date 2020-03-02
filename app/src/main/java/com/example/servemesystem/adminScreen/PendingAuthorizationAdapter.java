package com.example.servemesystem.adminScreen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.servemesystem.PendingAuthServiceProviderDetails;
import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceProvider;

import java.util.List;

public class PendingAuthorizationAdapter extends RecyclerView.Adapter<PendingAuthorizationAdapter.ViewHolder> {

    private List<ServiceProvider> mData;
    private LayoutInflater mInflater;
    private AdminUpdates adminUpdates = new AdminUpdates();

    public PendingAuthorizationAdapter(Context context, List<ServiceProvider> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);;
    }

    @Override
    public PendingAuthorizationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.pending_authorization_adapter_activity, parent, false);
        return new PendingAuthorizationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingAuthorizationAdapter.ViewHolder holder, int position) {
        ServiceProvider serviceProvider = mData.get(position);
        if(serviceProvider.getIsVerified().equalsIgnoreCase("rejected")){
            holder.acceptBtn.setVisibility(View.INVISIBLE);
            holder.rejectBtn.setVisibility(View.INVISIBLE);
            holder.rejectTV.setVisibility(View.VISIBLE);
        } else {
            holder.acceptBtn.setVisibility(View.VISIBLE);
            holder.rejectBtn.setVisibility(View.VISIBLE);
            holder.rejectTV.setVisibility(View.INVISIBLE);
        }
        holder.userName = serviceProvider.getUserName();
        holder.companyNameET.setText(serviceProvider.getCompanyname());
        // holder.addressTV.setText(serviceProvider.getOfficeaddress());
        holder.cityTV.setText(serviceProvider.getCity());
        // holder.stateTV.setText(serviceProvider.getState());
        // holder.countryTV.setText("USA");
        //holder.serviceTypesTextView.setText();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        EditText companyNameET;
        Button acceptBtn, rejectBtn;
        TextView addressTV, cityTV, stateTV, countryTV, zipTV, phoneNumberTV, serviceCategoriesTV,rejectTV;
        String userName;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pendingAuthImageId);
            companyNameET = itemView.findViewById(R.id.pendingAuthCompanyName);
            addressTV = itemView.findViewById(R.id.pendingAuthAddressID);
            cityTV= itemView.findViewById(R.id.pendingAuthCityID);
            stateTV = itemView.findViewById(R.id.pendingAuthStateID);
            countryTV = itemView.findViewById(R.id.pendingAuthCountryID);
            zipTV = itemView.findViewById(R.id.pendingAuthZipID);
            phoneNumberTV = itemView.findViewById(R.id.pendingAuthPhoneNumberID);
            serviceCategoriesTV = itemView.findViewById(R.id.pendingAuthServiceCategories);
            acceptBtn = itemView.findViewById(R.id.pendingAuthAcceptBtn);
            rejectBtn = itemView.findViewById(R.id.pendingAuthRejectBtn);
            itemView.setOnClickListener(this);
            rejectTV = itemView.findViewById(R.id.rejectedTV);
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adminUpdates.approveServiceProvider(userName);
                }
            });
            rejectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adminUpdates.rejectServiceProvider(userName);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PendingAuthServiceProviderDetails.class);
            intent.putExtra("c1",((EditText)(itemView.findViewById(R.id.pendingAuthCompanyName))).getText().toString());
            intent.putExtra("c2",((TextView)(itemView.findViewById(R.id.pendingAuthAddressID))).getText().toString());
            intent.putExtra("c3",((TextView)(itemView.findViewById(R.id.pendingAuthStateID))).getText().toString());
            intent.putExtra("c4",((TextView)(itemView.findViewById(R.id.pendingAuthPhoneNumberID))).getText().toString());
            view.getContext().startActivity(intent);
        }
    }

}
