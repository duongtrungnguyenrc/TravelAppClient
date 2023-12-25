package com.main.travelApp.services.api;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.PostDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPostService {
    @GET("blog/latest")
    Call<BaseResponse<List<MinimizePost>>> getNewestPosts();
    @GET("blog/all")
    Call<BaseResponse<AllPostResponse>> getAllPosts(@Query("page") int page, @Query("limit") int limit);
    @GET("blog/top")
    Call<BaseResponse<List<MinimizePost>>> getTopPosts();
    @GET("blog/{id}")
    Call<BaseResponse<PostDetailResponse>> getPostById(@Path("id") long id);
    @POST("blog/add-view/{id}")
    Call<BaseResponse<Object>> addView(@Path("id") Long id);
}
