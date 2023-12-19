package com.main.travelApp.repositories.impls;

import android.util.Log;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.request.AuthenticationRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IAuthService;
import com.main.travelApp.services.auth.AuthManager;

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
                    action.onFailure(response.message());
                    Log.d("query-status", response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AuthInstance>> call, Throwable t) {
                Log.d("query-status", t.getMessage());
                action.onFailure(t.getMessage());
            }
        });
    }
}
