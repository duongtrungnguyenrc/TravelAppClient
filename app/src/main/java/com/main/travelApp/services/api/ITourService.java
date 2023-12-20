package com.main.travelApp.services.api;

import com.main.travelApp.models.Place;
import com.main.travelApp.models.Tour;
import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.TourDateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITourService {
    @GET("tour/all")
    Call<BaseResponse<AllTourResponse>> getAllTours(@Query("page") int page, @Query("limit") int limit);
    @GET("tour/top-destination")
    Call<BaseResponse<List<Place>>> getTopDestination();

    @GET("tour/detail")
    Call<BaseResponse<Tour>> get(@Query("id") long id);

    @GET("tour/{id}/tour-date")
    Call<BaseResponse<TourDateResponse>> getDates(@Path("id") long id);
}
