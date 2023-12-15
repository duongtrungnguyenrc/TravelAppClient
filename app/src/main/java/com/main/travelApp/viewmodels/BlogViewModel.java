package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.interfaces.PostRepository;

import java.util.List;

public class BlogViewModel extends ViewModel {
    private PostRepository postRepository;
    private LiveData<List<GeneralPost>> posts;

    public BlogViewModel(){
        postRepository = PostRepositoryImpl.getInstance();
        posts = postRepository.findNewestPosts();
    }

    public LiveData<List<GeneralPost>> getNewestPosts(){
        return posts;
    }

}
