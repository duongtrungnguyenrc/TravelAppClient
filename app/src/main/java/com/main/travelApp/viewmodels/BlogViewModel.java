package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.response.AllPostResponse;

import java.util.List;

public class BlogViewModel extends ViewModel {
    private PostRepository postRepository;
    private LiveData<List<MinimizePost>> newestPosts;
    private MutableLiveData<AllPostResponse> allPostsResponse;
    private LiveData<List<MinimizePost>> topPosts;
    private int allPostPage;
    private int allPostLimit;

    public BlogViewModel(){
        allPostPage = 1;
        allPostLimit = 5;

        postRepository = PostRepositoryImpl.getInstance();

        newestPosts = postRepository.findNewestPosts();
        allPostsResponse = postRepository.findAll(allPostPage, allPostLimit);
        topPosts = postRepository.findTopPosts();
    }

    public int getAllPostPage() {
        return allPostPage;
    }

    public void setAllPostPage(int allPostPage) {
        this.allPostPage = allPostPage;
        postRepository.findAll(this.allPostPage, this.allPostLimit).observeForever(response -> {
            allPostsResponse.setValue(response);
        });
    }

    public int getAllPostLimit() {
        return allPostLimit;
    }

    public void setAllPostLimit(int allPostLimit) {
        this.allPostLimit = allPostLimit;
    }

    public LiveData<List<MinimizePost>> getNewestPosts(){
        return newestPosts;
    }

    public MutableLiveData<AllPostResponse> getAllPosts(){
        return allPostsResponse;
    }

    public LiveData<List<MinimizePost>> getTopPosts() {
        return topPosts;
    }
}
