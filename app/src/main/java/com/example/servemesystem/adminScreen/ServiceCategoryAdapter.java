package com.example.servemesystem.adminScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.servemesystem.PendingAuthServiceProviderDetails;
import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceCategory;
import com.example.servemesystem.domain.ServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.ViewHolder> {
    private ArrayList<ServiceCategory> mData;
    private LayoutInflater mInflater;

    public ServiceCategoryAdapter(Context context, ArrayList<ServiceCategory> mData) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);;
    }

    @Override
    public ServiceCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.activity_service_category_adapter, parent, false);
        return new ServiceCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceCategoryAdapter.ViewHolder holder, int position) {
        ServiceCategory serviceCategory = mData.get(position);
        holder.serviceCategoryName.setText(serviceCategory.getServiceCategoryName());
        holder.serviceCategoryDescription.setText(serviceCategory.getServiceCategoryDescription());
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

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pendingAuthImageId);
            serviceCategoryName = itemView.findViewById(R.id.serviceCategoryName);
            serviceCategoryDescription = itemView.findViewById(R.id.serviceCategoryDescription);
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
