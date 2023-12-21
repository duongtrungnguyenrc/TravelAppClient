package com.main.travelApp.viewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.User;
import com.main.travelApp.utils.SharedPreferenceKeys;

public class ProfileViewModel extends ViewModel {
    private SharedPreferences sharedPreferences;
    private User currentUser;

    public ProfileViewModel(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        bindCurrentUser();
    }
    private void bindCurrentUser(){
        currentUser = new User();
        currentUser.setFullName(sharedPreferences.getString(SharedPreferenceKeys.USER_FULL_NAME, ""));
        currentUser.setAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_AVATAR, ""));
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
