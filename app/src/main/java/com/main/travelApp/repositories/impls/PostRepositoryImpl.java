package com.main.travelApp.repositories.impls;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.PostDetailResponse;
import com.main.travelApp.ui.services.api.APIClient;
import com.main.travelApp.ui.services.api.IPostService;

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
    public LiveData<List<MinimizePost>> findNewestPosts() {
        MutableLiveData<List<MinimizePost>> posts = new MutableLiveData<>();
        Call<BaseResponse<List<MinimizePost>>> call = postService.getNewestPosts();
        call.enqueue(new Callback<BaseResponse<List<MinimizePost>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MinimizePost>>> call, Response<BaseResponse<List<MinimizePost>>> response) {
                Log.d("POST_findNewestPosts", "onResponse: " + response.message());
                if(response.isSuccessful()){
                    posts.setValue(response.body().getData());
                }else{
                    Log.d("POST_findNewestPosts", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MinimizePost>>> call, Throwable t) {
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
    public LiveData<List<MinimizePost>> findTopPosts() {
        MutableLiveData<List<MinimizePost>> posts = new MutableLiveData<>();
        Call<BaseResponse<List<MinimizePost>>> call = postService.getTopPosts();
        call.enqueue(new Callback<BaseResponse<List<MinimizePost>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<MinimizePost>>> call, Response<BaseResponse<List<MinimizePost>>> response) {
                if(response.isSuccessful()){
                    posts.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<MinimizePost>>> call, Throwable t) {
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

    @Override
    public void addPostView(Long id, ActionCallback<String> callback) {
        Call<BaseResponse<Object>> call = postService.addView(id);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFailure("Không tìm thấy bài viết có id: " + id);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                callback.onFailure(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
