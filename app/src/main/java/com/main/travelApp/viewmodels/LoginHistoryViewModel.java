package com.main.travelApp.viewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.LoginHistory;
import com.main.travelApp.repositories.impls.UserRepositoryImpl;
import com.main.travelApp.repositories.interfaces.UserRepository;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.util.List;

public class LoginHistoryViewModel extends ViewModel {
    private final UserRepository repository;
    private LiveData<List<LoginHistory>> loginHistory;
    private SharedPreferences sharedPreferences;

    public LoginHistoryViewModel(){
        repository = UserRepositoryImpl.getInstance();
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public LiveData<List<LoginHistory>> getLoginHistory() {
        return loginHistory = repository.getLoginHistory(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""));
    }

    public void setLoginHistory(LiveData<List<LoginHistory>> loginHistory) {
        this.loginHistory = loginHistory;
    }
}
