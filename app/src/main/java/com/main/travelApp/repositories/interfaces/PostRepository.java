package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.response.BaseResponse;

import java.util.List;

public interface PostRepository {
    public LiveData<List<GeneralPost>> findNewestPosts();
}
