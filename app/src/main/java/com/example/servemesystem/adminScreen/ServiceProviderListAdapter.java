package com.example.servemesystem.adminScreen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceProvider;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceProviderListAdapter extends RecyclerView.Adapter<ServiceProviderListAdapter.ViewHolder> {

    private List<ServiceProvider> mData;
    private LayoutInflater mInflater;

    public ServiceProviderListAdapter(Context context, List<ServiceProvider> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);;
    }

    @Override
    public ServiceProviderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_service_provider_list_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceProviderListAdapter.ViewHolder holder, int position) {
        ServiceProvider serviceProvider = mData.get(position);
        holder.companyNameET.setText(serviceProvider.getCompanyname());
        holder.addressTV.setText(serviceProvider.getOfficeaddress()+" ,");
        holder.cityTV.setText(serviceProvider.getCity()+" ,");
        holder.stateTV.setText(serviceProvider.getState()+" ,");
        holder.countryTV.setText("USA");
        holder.phoneNumberTV.setText(serviceProvider.getOfficenumber());
        holder.zipTV.setText(serviceProvider.getZipCode());
        if(!serviceProvider.getDp().isEmpty()){
            Picasso.get().load(serviceProvider.getDp()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        EditText companyNameET;
        TextView addressTV, cityTV, stateTV, countryTV, zipTV, phoneNumberTV, serviceCategoriesTV;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageId);
            companyNameET = itemView.findViewById(R.id.companyName);
            addressTV = itemView.findViewById(R.id.addressID);
            cityTV= itemView.findViewById(R.id.cityID);
            stateTV = itemView.findViewById(R.id.stateID);
            countryTV = itemView.findViewById(R.id.countryID);
            zipTV = itemView.findViewById(R.id.zipID);
            phoneNumberTV = itemView.findViewById(R.id.phonrNumberID);
            serviceCategoriesTV = itemView.findViewById(R.id.serviceCategories);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AdminServiceProviderDetails.class);
            intent.putExtra("c1",((EditText)(itemView.findViewById(R.id.companyName))).getText().toString());
            intent.putExtra("c2",((TextView)(itemView.findViewById(R.id.addressID))).getText().toString());
            intent.putExtra("c3",((TextView)(itemView.findViewById(R.id.stateID))).getText().toString());
            intent.putExtra("c4",((TextView)(itemView.findViewById(R.id.phonrNumberID))).getText().toString());
            view.getContext().startActivity(intent);
        }
    }
}
