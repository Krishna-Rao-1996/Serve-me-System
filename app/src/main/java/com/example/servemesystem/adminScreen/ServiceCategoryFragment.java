package com.example.servemesystem.adminScreen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.servemesystem.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceCategoryFragment extends Fragment {


    public ServiceCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.service_category_fragment, container, false);
    }

}
