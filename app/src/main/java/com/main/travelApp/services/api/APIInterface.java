package com.main.travelApp.services.api;

import com.main.travelApp.models.Tour;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/api/tour/all")
    Call<List<Tour>> getAllTours(@Query("page") int page, @Query("limit") int limit);

}

