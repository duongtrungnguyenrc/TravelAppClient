package com.main.travelApp.repositories.impls;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.response.BaseResponse;
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
}