package com.main.travelApp.viewmodels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.request.ConfirmCodeRequest;
import com.main.travelApp.request.ResetPasswordRequest;
import com.main.travelApp.request.SignUpRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SignUpResponse;
import com.main.travelApp.ui.activities.LoginActivity;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSignUpSuccess;
    private AuthRepository authRepository;
    private String confirmToken;
    private String forgotPassToken;
    private Context context;
    private MutableLiveData<Boolean> isResetPasswordCodeCorrect;
    private MutableLiveData<Boolean> isResetPasswordMailSent;
    private String resetPasswordEmail;
    public SignUpViewModel(){
        authRepository = AuthRepositoryImpl.getInstance();
        isSignUpSuccess = new MutableLiveData<>();
        isSignUpSuccess.setValue(false);
        isResetPasswordMailSent = new MutableLiveData<>();
        isResetPasswordMailSent.setValue(false);
        isResetPasswordCodeCorrect = new MutableLiveData<>();
        isResetPasswordCodeCorrect.setValue(false);
        confirmToken = "";
        forgotPassToken = "";
        resetPasswordEmail = "";
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MutableLiveData<Boolean> getIsSignUpSuccess() {
        return isSignUpSuccess;
    }

    public void signUp(SignUpRequest request){
        authRepository.signUp(request, new ActionCallback<SignUpResponse>() {
            @Override
            public void onSuccess(SignUpResponse result) {
                isSignUpSuccess.setValue(true);
                confirmToken = result.getConfirmToken();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Integer status, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void validateConfirmCode(String confirmCode){
        ConfirmCodeRequest request = new ConfirmCodeRequest();
        request.setActivateCode(confirmCode);
        request.setToken(confirmToken);
        authRepository.confirmCode(request, new ActionCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Kích hoạt tài khoản thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateConfirmCode(String confirmCode, AlertDialog dialog){
        ConfirmCodeRequest request = new ConfirmCodeRequest();
        request.setActivateCode(confirmCode);
        request.setToken(confirmToken);
        authRepository.confirmCode(request, new ActionCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Kích hoạt tài khoản thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
    public void sendForgotPasswordMail(String email){
        authRepository.getResetPasswordToken(email, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                forgotPassToken = result;
                isResetPasswordMailSent.setValue(true);
                resetPasswordEmail = email;
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void validateForgotPassword(String confirmCode){
        ConfirmCodeRequest request = new ConfirmCodeRequest();
        request.setActivateCode(confirmCode);
        request.setToken(forgotPassToken);
        authRepository.confirmCode(request, new ActionCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess() {
                isResetPasswordCodeCorrect.setValue(true);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPassword(String newPassword){
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setEmail(resetPasswordEmail);
        request.setPassword(newPassword);
        authRepository.resetPassword(request, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public MutableLiveData<Boolean> getIsResetPasswordCodeCorrect() {
        return isResetPasswordCodeCorrect;
    }

    public MutableLiveData<Boolean> getIsResetPasswordMailSent() {
        return isResetPasswordMailSent;
    }
}
