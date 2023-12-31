package com.main.travelApp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.main.travelApp.R;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.databinding.ActivityMainBinding;
import com.main.travelApp.ui.fragments.BlogFragment;
import com.main.travelApp.ui.fragments.ExploreFragment;
import com.main.travelApp.ui.fragments.HomeFragment;
import com.main.travelApp.ui.fragments.ProfileFragment;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.HomeViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private BlogFragment blogFragment;
    private ExploreFragment exploreFragment;
    private ProfileFragment profileFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE);
        loggedInFilter();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());
        init();
        getFCMToken();
    }

    private void init() {
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

    public void changeFragment(int fragmentIndex){
        switch (fragmentIndex){
            case 1 -> {
                binding.bottomNavView.setSelectedItemId(R.id.menuItemHome);
                replaceFragment(homeFragment);
            }
            case 2 -> {
                binding.bottomNavView.setSelectedItemId(R.id.menuItemExplore);
                replaceFragment(exploreFragment);
            }
            case 3 -> {
                binding.bottomNavView.setSelectedItemId(R.id.menuItemBlog);
                replaceFragment(blogFragment);
            }
            case 4 -> {
                binding.bottomNavView.setSelectedItemId(R.id.menuItemProfile);
                replaceFragment(profileFragment);
            }
        }
    }
    private void loggedInFilter(){
        if(sharedPreferences.getString("accessToken", null) == null){
            clearUserPrefs();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void clearUserPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult();
                Log.i("token", token);

            }
            else {
                Log.e("token", task.getException().getMessage());
            }
        });
    }
}