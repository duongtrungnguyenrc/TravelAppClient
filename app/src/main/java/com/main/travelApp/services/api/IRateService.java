package com.main.travelApp.services.api;

import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.RateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRateService {
    @GET("rate/blog/{id}")
    Call<BaseResponse<RateResponse>> getRateByBlogId(@Path("id") long id, @Query("page") int page, @Query("limit") int limit);
}
