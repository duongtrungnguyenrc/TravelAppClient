package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.PostDetailResponse;

import java.util.List;

public interface PostRepository {
    public LiveData<List<MinimizePost>> findNewestPosts();
    public MutableLiveData<AllPostResponse> findAll(int page, int limit);
    public LiveData<List<MinimizePost>> findTopPosts();
    public MutableLiveData<PostDetailResponse> findById(long id);
}
