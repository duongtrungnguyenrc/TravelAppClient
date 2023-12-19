package com.main.travelApp.services.api;

import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.request.AuthenticationRequest;
import com.main.travelApp.response.AuthenticationResponse;
import com.main.travelApp.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("auth/signin")
    Call<BaseResponse<AuthInstance>> authentication(@Body AuthenticationRequest payload);
}
