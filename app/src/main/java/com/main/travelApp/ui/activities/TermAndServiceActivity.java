package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityTermAndServiceBinding;

public class TermAndServiceActivity extends AppCompatActivity {
    private ActivityTermAndServiceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermAndServiceBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.tbTermsAndServices);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        setContentView(binding.getRoot());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}