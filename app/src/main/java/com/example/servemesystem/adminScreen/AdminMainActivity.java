package com.example.servemesystem.adminScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.servemesystem.Homepage.ServiceProviderHomeActivity;
import com.example.servemesystem.LoginActivity;
import com.example.servemesystem.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class AdminMainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabItem1, tabItem2, tabItem3;

    public AdminPageAdapter adminPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);

        tabItem1 = findViewById(R.id.tabItem1);
        tabItem2 = findViewById(R.id.tabItem2);
        tabItem3 = findViewById(R.id.tabItem3);

        viewPager = findViewById(R.id.viewPager);

        adminPageAdapter = new AdminPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adminPageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    adminPageAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 1){
                    adminPageAdapter.notifyDataSetChanged();
                }else if(tab.getPosition() == 2){
                    adminPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_provider_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
                SharedPreferences prefs = getSharedPreferences("currUser", MODE_PRIVATE);
                prefs.edit().clear();
                prefs.edit().apply();
                Intent myInt = new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(myInt);
                return true;
        }
}
