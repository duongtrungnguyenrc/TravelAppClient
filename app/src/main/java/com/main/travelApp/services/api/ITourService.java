package com.main.travelApp.services.api;

import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITourService {
    @GET("tour/all")
    Call<BaseResponse<AllTourResponse>> getAllTours(@Query("page") int page, @Query("limit") int limit);
}
