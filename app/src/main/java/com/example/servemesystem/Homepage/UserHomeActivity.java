package com.example.servemesystem.Homepage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.servemesystem.Homepage.ui.home.HomeViewModel;
import com.example.servemesystem.LoginActivity;
import com.example.servemesystem.R;
import com.example.servemesystem.UserModel;
import com.example.servemesystem.domain.ConstantResources;

import android.util.Log;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private String TAG = "UserHomeActivity.java";
    private ArrayList<String> serviceType = new ArrayList<>();
    private ArrayList<String> uName = new ArrayList<>();
    private ArrayList<String> serviceDescription =  new ArrayList<>();
    private Context mContext;
    String userName,services,verified,dp,emailAddress,fullName;
    ImageView profile, serviceImage;
    TextView emailTV,fullNameTV;
    private AppBarConfiguration mAppBarConfiguration;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        getServiceProviderTypes();
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_past_orders,
                R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
        userName = prefs.getString("userName", null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.fragment_prev_orders:
                        Intent myint = new Intent(UserHomeActivity.this,PastUserOrders.class);
                        startActivity(myint);
                        break;
                    case R.id.fragment_logout:
                        SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
                        prefs.edit().clear();
                        prefs.edit().apply();
                        Intent myInt = new Intent(UserHomeActivity.this, LoginActivity.class);
                        startActivity(myInt);
                        break;
                }
                drawer.closeDrawers();
                return false;
            }
        });
        View headerview = navigationView.getHeaderView(0);
        profile= headerview.findViewById(R.id.profilePicture);
        emailTV= headerview.findViewById(R.id.emailTV);
        fullNameTV = headerview.findViewById(R.id.fullNameTV);

        myRef.child(ConstantResources.USERS).child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                dp=userModel.getDp();
                emailAddress=userModel.getEmail();
                fullName=userModel.getFullName();
                emailTV.setText(emailAddress);
                fullNameTV.setText(fullName);
                if(null != dp){
                    Picasso.get().load(dp).into(profile);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myint = new Intent(UserHomeActivity.this, UpdateProfile.class);
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
        UserHomeActivityAdapter adapter = new UserHomeActivityAdapter(this, uName, serviceType,serviceDescription);
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
