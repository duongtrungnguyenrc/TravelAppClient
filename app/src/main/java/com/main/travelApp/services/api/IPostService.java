package com.main.travelApp.services.api;

import androidx.lifecycle.LiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.response.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPostService {
    @GET("blog/latest")
    Call<BaseResponse<List<GeneralPost>>> getNewestPosts();
}
