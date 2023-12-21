package com.main.travelApp.services.api;

import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearchService {
    @GET("tour/search-contain-blog")
    Call<BaseResponse<SearchResponse>> search(@Query("key") String keyword);
}
