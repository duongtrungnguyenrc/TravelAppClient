package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.response.AllPostResponse;
import com.main.travelApp.response.BaseResponse;

import java.util.List;

public interface PostRepository {
    public LiveData<List<GeneralPost>> findNewestPosts();
    public MutableLiveData<AllPostResponse> findAll(int page, int limit);
}
