package com.main.travelApp.repositories.impls;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.PostDetailResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IPostService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepositoryImpl implements PostRepository {
    private IPostService postService;
    private static PostRepositoryImpl instance;
    private PostRepositoryImpl(){
        this.postService = APIClient.getClient().create(IPostService.class);
    }

    public static PostRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new PostRepositoryImpl();
    }

    @Override
    public LiveData<List<GeneralPost>> findNewestPosts() {
        MutableLiveData<List<GeneralPost>> posts = new MutableLiveData<>();
        Call<BaseResponse<List<GeneralPost>>> call = postService.getNewestPosts();
        call.enqueue(new Callback<BaseResponse<List<GeneralPost>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<GeneralPost>>> call, Response<BaseResponse<List<GeneralPost>>> response) {
                Log.d("POST_findNewestPosts", "onResponse: " + response.message());
                if(response.isSuccessful() && response != null){
                    posts.setValue(response.body().getData());
                }else{
                    Log.d("POST_findNewestPosts", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<GeneralPost>>> call, Throwable t) {
                t.printStackTrace();
                posts.setValue(null);
            }
        });
        return posts;
    }

    @Override
    public MutableLiveData<AllPostResponse> findAll(int page, int limit) {
        MutableLiveData<AllPostResponse> liveData = new MutableLiveData<>();
        Call<BaseResponse<AllPostResponse>> call = postService.getAllPosts(page, limit);
        call.enqueue(new Callback<BaseResponse<AllPostResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AllPostResponse>> call, Response<BaseResponse<AllPostResponse>> response) {
                Log.d("POST_findAll", "onResponse: " + response.message());
                if(response.isSuccessful() && response != null){
                    liveData.setValue(response.body().getData());
                }else{
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AllPostResponse>> call, Throwable t) {
                t.printStackTrace();
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    @Override
    public LiveData<List<GeneralPost>> findTopPosts() {
        MutableLiveData<List<GeneralPost>> posts = new MutableLiveData<>();
        Call<BaseResponse<List<GeneralPost>>> call = postService.getTopPosts();
        call.enqueue(new Callback<BaseResponse<List<GeneralPost>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<GeneralPost>>> call, Response<BaseResponse<List<GeneralPost>>> response) {
                if(response.isSuccessful()){
                    posts.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<GeneralPost>>> call, Throwable t) {
                posts.setValue(null);
            }
        });
        return posts;
    }

    @Override
    public MutableLiveData<PostDetailResponse> findById(long id) {
        MutableLiveData<PostDetailResponse> postResponse = new MutableLiveData<>();
        Call<BaseResponse<PostDetailResponse>> call = postService.getPostById(id);
        call.enqueue(new Callback<BaseResponse<PostDetailResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<PostDetailResponse>> call, Response<BaseResponse<PostDetailResponse>> response) {
                if(response.isSuccessful()){
                    postResponse.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<PostDetailResponse>> call, Throwable t) {
                postResponse.setValue(null);
            }
        });
        return postResponse;
    }
}
