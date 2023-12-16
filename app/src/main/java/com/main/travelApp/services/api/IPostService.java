package com.main.travelApp.services.api;

import androidx.lifecycle.LiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.PostDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPostService {
    @GET("blog/latest")
    Call<BaseResponse<List<GeneralPost>>> getNewestPosts();
    @GET("blog/all")
    Call<BaseResponse<AllPostResponse>> getAllPosts(@Query("page") int page, @Query("limit") int limit);
    @GET("blog/top")
    Call<BaseResponse<List<GeneralPost>>> getTopPosts();
    @GET("blog/{id}")
    Call<BaseResponse<PostDetailResponse>> getPostById(@Path("id") long id);
}
