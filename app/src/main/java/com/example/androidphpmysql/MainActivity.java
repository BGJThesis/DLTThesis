package com.example.androidphpmysql;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        TransactionFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{

    private TabLayout menuBar;
    private ViewPager viewPager;
    private FirebaseAuth firebaseAuth;

    private int[] menuBarIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_local_atm_black_24dp,
            R.drawable.ic_person_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager =(ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        menuBar = (TabLayout) findViewById(R.id.tabLayout);
        menuBar.setupWithViewPager(viewPager);

        setupMenuBarIcons();

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();
    }

    private void setupMenuBarIcons() {
        menuBar.getTabAt(0).setIcon(menuBarIcons[0]);
        menuBar.getTabAt(1).setIcon(menuBarIcons[1]);
        menuBar.getTabAt(2).setIcon(menuBarIcons[2]);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
