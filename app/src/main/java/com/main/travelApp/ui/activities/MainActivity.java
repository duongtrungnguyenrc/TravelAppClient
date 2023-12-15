package com.main.travelApp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.main.travelApp.R;
import com.main.travelApp.models.Tour;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.APIInterface;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.databinding.ActivityMainBinding;
import com.main.travelApp.ui.fragments.BlogFragment;
import com.main.travelApp.ui.fragments.ExploreFragment;
import com.main.travelApp.ui.fragments.HomeFragment;
import com.main.travelApp.ui.fragments.ProfileFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private HomeFragment homeFragment;
    private BlogFragment blogFragment;
    private ExploreFragment exploreFragment;
    private ProfileFragment profileFragment;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        // test call api

//        apiInterface = APIClient.getClient().create(APIInterface.class);
//
//        Call<List<Tour>> tours = apiInterface.getAllTours(1, 20);
//        tours.enqueue(new Callback<List<Tour>>() {
//            @Override
//            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
//                if (response.isSuccessful()) {
//                    Log.d("tour", response.body().toString());
//                }
//                else {
//                    Log.d("tour", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Tour>> call, Throwable t) {
//                Log.d("tour", t.getMessage());
//
//            }
//        });

        // end test
    }

    private void init(){
        homeFragment = new HomeFragment();
        blogFragment = new BlogFragment();
        exploreFragment = new ExploreFragment();
        profileFragment = new ProfileFragment();

        replaceFragment(homeFragment);

        binding.btnSearch.setOnClickListener((view) -> {
            BottomSheet.show(this);
        });

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