package com.example.androidphpmysql;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidphpmysql.HomeFragment;
import com.example.androidphpmysql.ProfileFragment;
import com.example.androidphpmysql.TransactionFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[]childFragments;

    public ViewPagerAdapter (FragmentManager fm){
        super(fm);
        childFragments = new Fragment[]{
                new HomeFragment(),
                new TransactionFragment(),
                new ProfileFragment()
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Home";
        } else if (position == 1){
            return "Transact";
        } else{
            return "Profile";
        }
    }
}
