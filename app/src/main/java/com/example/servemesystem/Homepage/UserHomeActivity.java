package com.example.servemesystem.Homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.servemesystem.Homepage.ui.home.HomeViewModel;
import com.example.servemesystem.R;
import com.example.servemesystem.UpdateProfileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private String TAG = "UserHomeActivity.java";
    private ArrayList<String> serviceType = new ArrayList<>();
    private ArrayList<String> userName= new ArrayList<>();
    private ArrayList<String> serviceDescription =  new ArrayList<>();
    private Context mContext;

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
//    String userName,services;
    ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        getServiceProviderTypes();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pooja, what is this behaviour?", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.fragment_prev_orders, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.fragment_logout, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerview = navigationView.getHeaderView(0);
        TextView profilename = (TextView) headerview.findViewById(R.id.emailHeader);
        profilename.setText("Behaviour Pooja");
        profile= headerview.findViewById(R.id.profilePicture);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(UserHomeActivity.this, UpdateProfileActivity.class);
                startActivity(myint);
            }
        });
    }

    private void displayDataInUserHomePage(String typeName, String typeDescription) {
        homeViewModel = new HomeViewModel();

    }


    private void getServiceProviderTypes() {
        myRef.child("Service_Provider_Types").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i(TAG, "Inside getServiceProviderTypes");
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    String serviceProviderType = postSnapshot.getKey();
                    serviceType.add(serviceProviderType);
                    String serviceProviderDescription = postSnapshot.getValue(String.class);
                    serviceDescription.add(serviceProviderDescription);
                }
                initRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });
    }

    private void initRecyclerView() {
        Log.d(TAG, "UserHomeActivity: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        UserHomeActivityAdapter adapter = new UserHomeActivityAdapter(this, userName, serviceType,serviceDescription);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
