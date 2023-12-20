package com.main.travelApp.services.api;

import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.request.AuthenticationRequest;
import com.main.travelApp.request.ConfirmCodeRequest;
import com.main.travelApp.request.ResetPasswordRequest;
import com.main.travelApp.request.SignUpRequest;
import com.main.travelApp.request.TokenAuthRequest;
import com.main.travelApp.response.AuthenticationResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IAuthService {
    @POST("auth/signin")
    Call<BaseResponse<AuthInstance>> authentication(@Body AuthenticationRequest payload);
    @POST("auth/validate-token-id")
    Call<BaseResponse<AuthInstance>> authWithGoogleToken(@Body TokenAuthRequest payload);
    @POST("auth/verify-header")
    Call<BaseResponse<Object>> verify(@Header("Authorization") String accessToken);
    @POST("auth/signup")
    Call<BaseResponse<SignUpResponse>> signUp(@Body SignUpRequest request);
    @POST("auth/activate")
    Call<BaseResponse<Object>> validateConfirmCode(@Body ConfirmCodeRequest request);
    @POST("auth/send-mail/change-password-code/{email}")
    Call<BaseResponse<String>> sendResetPasswordMail(@Path("email") String email);
    @POST("auth/change-password-with-email")
    Call<BaseResponse<Object>> resetPassword(@Body ResetPasswordRequest request);
}
