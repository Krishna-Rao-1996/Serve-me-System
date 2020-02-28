package com.example.servemesystem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.servemesystem.adminScreen.PendingAuthorizationFragment;
import com.example.servemesystem.adminScreen.ServiceCategoryFragment;
import com.example.servemesystem.adminScreen.ServiceProviderListFragment;

public class AdminPageAdapter extends FragmentPagerAdapter {
    int numOfTabs;

    public AdminPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ServiceProviderListFragment();
            case 1:
                return new PendingAuthorizationFragment();
            case 2:
                return new ServiceCategoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
