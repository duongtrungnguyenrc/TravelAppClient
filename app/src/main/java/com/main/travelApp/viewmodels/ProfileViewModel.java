package com.main.travelApp.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.User;
import com.main.travelApp.repositories.impls.UserRepositoryImpl;
import com.main.travelApp.repositories.interfaces.UserRepository;
import com.main.travelApp.request.UpdateUserRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.utils.SharedPreferenceKeys;

public class ProfileViewModel extends ViewModel {
    private SharedPreferences sharedPreferences;
    private User currentUser;
    private UserRepository userRepository;
    private Context context;
    private MutableLiveData<Boolean> isUserUpdated;

    public ProfileViewModel(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        userRepository = UserRepositoryImpl.getInstance();
        isUserUpdated = new MutableLiveData<>();
        isUserUpdated.setValue(false);
        bindCurrentUser();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void bindCurrentUser(){
        if(currentUser == null)
            currentUser = new User();
        currentUser.setFullName(sharedPreferences.getString(SharedPreferenceKeys.USER_FULL_NAME, ""));
        currentUser.setAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_AVATAR, ""));
        currentUser.setAddress(sharedPreferences.getString(SharedPreferenceKeys.USER_ADDRESS, ""));
        currentUser.setEmail(sharedPreferences.getString(SharedPreferenceKeys.USER_EMAIL, ""));
        currentUser.setPhone(sharedPreferences.getString(SharedPreferenceKeys.USER_PHONE, ""));
    }

    private void updateUserSharedPrefs(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPreferenceKeys.USER_FULL_NAME, currentUser.getFullName());
        editor.putString(SharedPreferenceKeys.USER_PHONE, currentUser.getPhone());
        editor.putString(SharedPreferenceKeys.USER_ADDRESS, currentUser.getAddress());
        editor.apply();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Boolean> getIsUserUpdated() {
        return isUserUpdated;
    }

    public void updateUser(){
        UpdateUserRequest request = new UpdateUserRequest();
        request.setFullName(currentUser.getFullName());
        request.setAddress(currentUser.getAddress());
        request.setPhone(currentUser.getPhone());

        userRepository.update(
                sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""),
                request,
                new ActionCallback<BaseResponse<Object>>() {
                    @Override
                    public void onSuccess(BaseResponse<Object> result) {
                        Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
                        updateUserSharedPrefs();
                        isUserUpdated.setValue(true);
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        bindCurrentUser();
                        isUserUpdated.setValue(false);
                    }
                }
        );
    }
}
