package com.example.servemesystem.adminScreen;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.servemesystem.R;
import com.example.servemesystem.domain.ServiceProvider;

import java.util.List;

public class OperaterDataView extends RecyclerView.Adapter<OperaterDataView.ViewHolder> {

    private List<ServiceProvider> mData;
    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;

    // data is passed into the constructor
    OperaterDataView(Context context, List<ServiceProvider> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_admin_main, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ServiceProvider serviceProvider = mData.get(position);
        holder.companyNameTextView.setText(serviceProvider.getCompanyname());
        holder.officeNumberTextView.setText(serviceProvider.getOfficenumber());
        //holder.serviceTypesTextView.setText();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView companyNameTextView;
        TextView officeNumberTextView;
        TextView serviceTypesTextView;

        ViewHolder(View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.tvcompanyName);
            officeNumberTextView = itemView.findViewById(R.id.tvCompanyPhone);
            serviceTypesTextView = itemView.findViewById(R.id.tvServiceTypes);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
