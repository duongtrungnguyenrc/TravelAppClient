package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityTourDetailBinding;

public class TourDetailActivity extends AppCompatActivity {
    private ActivityTourDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        this.binding = ActivityTourDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fetchData();
    }

    private void fetchData() {

    }
}