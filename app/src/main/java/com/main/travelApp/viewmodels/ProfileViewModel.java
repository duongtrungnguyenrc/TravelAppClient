package com.main.travelApp.viewmodels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.User;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.repositories.impls.UserRepositoryImpl;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.repositories.interfaces.UserRepository;
import com.main.travelApp.request.ChangePasswordRequest;
import com.main.travelApp.request.UpdateUserRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileViewModel extends ViewModel {
    private SharedPreferences sharedPreferences;
    private User currentUser;
    private UserRepository userRepository;
    private AuthRepository authRepository;
    private Context context;
    private MutableLiveData<Boolean> isUserUpdated;
    private MutableLiveData<Boolean> isUserAvatarUpdated;

    public ProfileViewModel(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        userRepository = UserRepositoryImpl.getInstance();
        authRepository = AuthRepositoryImpl.getInstance();
        isUserUpdated = new MutableLiveData<>();
        isUserUpdated.setValue(false);
        isUserAvatarUpdated = new MutableLiveData<>();
        isUserAvatarUpdated.setValue(false);
        bindCurrentUser();
    }

    public MutableLiveData<Boolean> getIsUserAvatarUpdated() {
        return isUserAvatarUpdated;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void bindCurrentUser(){
        if(currentUser == null)
            currentUser = new User();
        currentUser.setId(sharedPreferences.getString(SharedPreferenceKeys.USER_ID, ""));
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

    public void changePassword(String oldPassword, String newPassword, AlertDialog dialog){
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);
        authRepository.changePassword(
                sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""),
                request,
                new ActionCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void updateUserAvatar(String imagePath){
        if(imagePath != null){
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Đang tải ảnh lên");
            dialog.show();
            File file = new File(imagePath);
            RequestBody requestFile =
                    RequestBody.create(MultipartBody.FORM, file);
            MultipartBody.Part imagePart =
                    MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
            userRepository.updateAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), imagePart, new ActionCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(context, "Cập nhật ảnh đại diện thành công!", Toast.LENGTH_SHORT).show();
                    sharedPreferences.edit()
                            .putString(SharedPreferenceKeys.USER_AVATAR, result)
                            .apply();
                    currentUser.setAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_AVATAR, ""));
                    isUserAvatarUpdated.setValue(true);
                    dialog.dismiss();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        } else{
            Toast.makeText(context, "Có lỗi xảy ra, vui lòng chọn ảnh lại!", Toast.LENGTH_SHORT).show();
        }
    }
}
