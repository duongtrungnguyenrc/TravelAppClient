package com.main.travelApp.services.api;

import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.response.AddRateResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.RateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRateService {
    @GET("rate/blog/{id}")
    Call<BaseResponse<RateResponse>> getRateByBlogId(@Header("Authorization") String accessToken, @Path("id") long id, @Query("page") int page, @Query("limit") int limit);
    @GET("rate/{id}")
    Call<BaseResponse<RateResponse>> getRateByTourId(@Path("id") long id, @Query("page") int page, @Query("limit") int limit);
    @POST("rate")
    Call<BaseResponse<AddRateResponse>> addRate(@Header("Authorization") String accessToken, @Body AddRateRequest request);
}
