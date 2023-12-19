package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.response.PostDetailResponse;
import com.main.travelApp.response.RateResponse;

public class BlogDetailViewModel extends ViewModel {
    private PostRepository postRepository;
    private RateRepository rateRepository;
    private MutableLiveData<PostDetailResponse> postDetailResponse;
    private MutableLiveData<RateResponse> rateResponse;
    private int commentPage;
    private long id;

    public BlogDetailViewModel(long id){
        this.id = id;
        rateRepository = RateRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        commentPage = 1;
        rateResponse = rateRepository.findByBlogId(this.id, this.commentPage, 6);
    }

    public int getCommentPage() {
        return commentPage;
    }

    public void setCommentPage(int commentPage) {
        this.commentPage = commentPage;
        rateRepository.findByBlogId(id, commentPage, 6).observeForever(data -> {
            rateResponse.setValue(data);
        });
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MutableLiveData<PostDetailResponse> getPostDetailResponse() {
        return postDetailResponse = postRepository.findById(id);
    }

    public MutableLiveData<RateResponse> getRateResponse() {
        return rateResponse;
    }
}
