package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityCheckOutBinding;
import com.main.travelApp.utils.ScreenManager;

public class CheckOutActivity extends AppCompatActivity {

    private ActivityCheckOutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());
    }
}