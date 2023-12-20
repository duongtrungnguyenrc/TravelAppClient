package com.main.travelApp.viewmodels.factories;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.main.travelApp.viewmodels.HomeViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final SharedPreferences sharedPreferences;

    public HomeViewModelFactory(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(HomeViewModel.class)){
            return (T) new HomeViewModel(sharedPreferences);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
