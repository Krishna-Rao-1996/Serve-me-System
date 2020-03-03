package com.example.servemesystem.Homepage.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.servemesystem.Homepage.UserHomeActivity;
import com.example.servemesystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private String TAG = "UserHomeActivity.java";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getServiceProviderTypes();
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
    private void displayDataInUserHomePage(String typeName, String typeDescription) {
        homeViewModel = new HomeViewModel();
        homeViewModel.displayNewModel(typeName, typeDescription);
    }


    private void getServiceProviderTypes() {
        myRef.child("Service_Provider_Types").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i(TAG, "Inside getServiceProviderTypes");
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String serviceProviderType = postSnapshot.getKey();
                    Log.i("UserHomeActivity","Type: "+serviceProviderType);
                    String serviceProviderDescription = postSnapshot.getValue(String.class);
                    Log.i("UserHomeActivity","Type desc: "+serviceProviderDescription);

//                    TextView newTextView = new TextView(HomeFragment.this);

                    homeViewModel.displayNewModel(serviceProviderType,serviceProviderDescription);
                    displayDataInUserHomePage(serviceProviderType, serviceProviderDescription);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });
    }
}