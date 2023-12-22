package com.main.travelApp.repositories.interfaces;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.request.ChangePasswordRequest;
import com.main.travelApp.request.ConfirmCodeRequest;
import com.main.travelApp.request.ResetPasswordRequest;
import com.main.travelApp.request.SignUpRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SignUpResponse;


public interface AuthRepository {
    public void authentication(String email, String password, ActionCallback<AuthInstance> action);
    public void authenticationWithGoogleToken(String tokenId, ActionCallback<AuthInstance> action);
    public void verifyToken(String accessToken, ActionCallback<Object> actionCallback);
    public void signUp(SignUpRequest request, ActionCallback<SignUpResponse> actionCallback);
    public void confirmCode(ConfirmCodeRequest request, ActionCallback<BaseResponse<Object>> callback);
    public void getResetPasswordToken(String email, ActionCallback<String> callback);
    public void resetPassword(ResetPasswordRequest request, ActionCallback<String> callback);
    public void changePassword(String accessToken, ChangePasswordRequest request, ActionCallback<String> callback);
}
