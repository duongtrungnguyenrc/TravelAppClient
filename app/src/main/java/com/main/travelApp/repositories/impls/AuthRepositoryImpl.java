package com.main.travelApp.repositories.impls;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.request.AuthenticationRequest;
import com.main.travelApp.request.ConfirmCodeRequest;
import com.main.travelApp.request.ResetPasswordRequest;
import com.main.travelApp.request.SignUpRequest;
import com.main.travelApp.request.TokenAuthRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SignUpResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IAuthService;
import com.main.travelApp.services.auth.AuthManager;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepositoryImpl implements AuthRepository {
    private final IAuthService authService;
    private static AuthRepositoryImpl instance;

    private AuthRepositoryImpl() {
        this.authService = APIClient.getClient().create(IAuthService.class);
    }

    public static AuthRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new AuthRepositoryImpl();
    }

    @Override
    public void authentication(String email, String password, ActionCallback<AuthInstance> action) {
        Call<BaseResponse<AuthInstance>> call = authService.authentication(new AuthenticationRequest(email, password));

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<AuthInstance>> call, Response<BaseResponse<AuthInstance>> response) {
                if (response.isSuccessful()) {
                    AuthManager.getInstance().setAuthInstance(response.body().getData());
                    action.onSuccess(response.body().getData());
                } else {
                    String errorBody = "";
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        action.onError(e);
                    }
                    Gson gson = new Gson();
                    BaseResponse<Object> errorResponse = gson.fromJson(errorBody, BaseResponse.class);
                    action.onFailure(response.code(), errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthInstance>> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void authenticationWithGoogleToken(String tokenId, ActionCallback<AuthInstance> action) {
        Call<BaseResponse<AuthInstance>> call = authService.authWithGoogleToken(new TokenAuthRequest(tokenId));
        call.enqueue(new Callback<BaseResponse<AuthInstance>>() {
            @Override
            public void onResponse(Call<BaseResponse<AuthInstance>> call, Response<BaseResponse<AuthInstance>> response) {
                if(response.isSuccessful()){
                    action.onSuccess(response.body().getData());
                }else{
                    String errorBody = "";
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        action.onFailure(e.getMessage());
                    }
                    Gson gson = new Gson();
                    BaseResponse<Object> errorResponse = gson.fromJson(errorBody, BaseResponse.class);
                    action.onFailure(response.code(), errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthInstance>> call, Throwable t) {
                Log.d("query-status", t.getMessage());
                action.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void verifyToken(String accessToken, ActionCallback<Object> actionCallback) {
        Call<BaseResponse<Object>> call = authService.verify(accessToken);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if(response.isSuccessful()){
                    actionCallback.onSuccess(true);
                }else{
                    actionCallback.onFailure(response.code(), "Fail");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                actionCallback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void signUp(SignUpRequest request, ActionCallback<SignUpResponse> actionCallback) {
        Call<BaseResponse<SignUpResponse>> call = authService.signUp(request);
        call.enqueue(new Callback<BaseResponse<SignUpResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<SignUpResponse>> call, Response<BaseResponse<SignUpResponse>> response) {
                if(response.isSuccessful()){
                    actionCallback.onSuccess(response.body().getData());
                }else{
                    actionCallback.onFailure(response.code(), response.message());
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<SignUpResponse>> call, Throwable t) {
                actionCallback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void confirmCode(ConfirmCodeRequest request, ActionCallback<BaseResponse<Object>> callback) {
        Call<BaseResponse<Object>> call = authService.validateConfirmCode(request);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    String errorBody = "";
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        callback.onFailure(e.getMessage());
                    }
                    Gson gson = new Gson();
                    BaseResponse<Object> errorResponse = gson.fromJson(errorBody, BaseResponse.class);
                    callback.onFailure(errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getResetPasswordToken(String email, ActionCallback<String> callback) {
        Call<BaseResponse<String>> call = authService.sendResetPasswordMail(email);
        call.enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body().getData());
                }else{
                    callback.onFailure("Tài khoản không tồn tại trong hệ thống!");
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra, vui lòng thử lại!");
            }
        });
    }

    @Override
    public void resetPassword(ResetPasswordRequest request, ActionCallback<String> callback) {
        Call<BaseResponse<Object>> call = authService.resetPassword(request);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body().getMessage());
                }else{
                    callback.onFailure("Tài khoản không tồn tại!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                t.printStackTrace();
                callback.onFailure("Có lỗi xảy ra, vui lòng thử lại sau!");
            }
        });
    }
}
