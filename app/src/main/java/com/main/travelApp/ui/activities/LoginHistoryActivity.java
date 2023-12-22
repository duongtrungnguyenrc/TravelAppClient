package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.adapters.LoginHistoryAdapter;
import com.main.travelApp.databinding.ActivityLoginHistoryBinding;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.LoginHistoryViewModel;

public class LoginHistoryActivity extends AppCompatActivity {
    private ActivityLoginHistoryBinding binding;
    private LoginHistoryAdapter adapter;
    private LoginHistoryViewModel viewModel;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginHistoryBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(LoginHistoryViewModel.class);
        sharedPreferences = getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE);
        viewModel.setSharedPreferences(sharedPreferences);
        setSupportActionBar(binding.tbLoginHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        setContentView(binding.getRoot());
        init();
    }

    private void init(){
        adapter = new LoginHistoryAdapter();
        adapter.setContext(this);

        viewModel.getLoginHistory().observe(this, loginHistory -> {
            adapter.setLoginHistories(loginHistory);
        });

        binding.rcvLoginHistory.setAdapter(adapter);
        binding.rcvLoginHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvLoginHistory.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}