package com.main.travelApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.main.travelApp.databinding.ActivityMainBinding;
import com.main.travelApp.fragments.BlogFragment;
import com.main.travelApp.fragments.ExploreFragment;
import com.main.travelApp.fragments.HomeFragment;
import com.main.travelApp.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private BlogFragment blogFragment;
    private ExploreFragment exploreFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        homeFragment = new HomeFragment();
        blogFragment = new BlogFragment();
        exploreFragment = new ExploreFragment();
        profileFragment = new ProfileFragment();

        replaceFragment(homeFragment);

        binding.bottomNavView.setBackground(null);
        binding.bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuItemHome){
                    replaceFragment(homeFragment);
                }else if(item.getItemId() == R.id.menuItemBlog){
                    replaceFragment(blogFragment);
                }else if(item.getItemId() == R.id.menuItemExplore){
                    replaceFragment(exploreFragment);
                }else if(item.getItemId() == R.id.menuItemProfile){
                    replaceFragment(profileFragment);
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}