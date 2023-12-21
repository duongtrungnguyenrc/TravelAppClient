package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.adapters.OrderAdapter;
import com.main.travelApp.databinding.ActivityUserOrdersBinding;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.UserOrderViewModel;

public class UserOrdersActivity extends AppCompatActivity {
    private ActivityUserOrdersBinding binding;
    private OrderAdapter adapter;
    private UserOrderViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserOrdersBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(UserOrderViewModel.class);
        viewModel.setSharedPreferences(getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE));
        viewModel.setContext(this);
        setSupportActionBar(binding.tbUserOrder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        adapter = new OrderAdapter();
        adapter.setContext(this);
        adapter.setLayoutInflater(getLayoutInflater());
        adapter.setFragmentManager(getSupportFragmentManager());
        adapter.setViewModel(viewModel);

        viewModel.getOrders().observe(this, orders -> {
            adapter.setOrders(orders);
        });

        binding.rcvOrderHistory.setItemAnimator(new DefaultItemAnimator());
        binding.rcvOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvOrderHistory.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}