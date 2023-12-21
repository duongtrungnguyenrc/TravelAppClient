package com.main.travelApp.viewmodels.factories;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.main.travelApp.viewmodels.ProfileViewModel;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private SharedPreferences sharedPreferences;
    public ProfileViewModelFactory(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel(sharedPreferences);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
