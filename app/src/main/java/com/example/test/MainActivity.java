package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.test.Fragments.MapsFragment;
import com.example.test.Fragments.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    final Fragment fragment1 = new NewsFragment();
    final Fragment fragment2 = new MapsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.NewsPage:
                    loadFragment(fragment1);
                    active = fragment1;
                    return true;
                case R.id.MapsPage:
                    loadFragment(fragment2);
                    active = fragment2;
                    return true;

            }
            return false;
        }
    };

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fm.beginTransaction().hide(active).show(fragment).commit();
            return true;
        }
        return false;
    }
}