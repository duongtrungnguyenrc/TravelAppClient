package com.main.travelApp.viewmodels;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.AuthInstance;
import com.main.travelApp.models.Rate;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.response.PostDetailResponse;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.ui.components.ExpiredDialog;

public class BlogDetailViewModel extends ViewModel {
    private PostRepository postRepository;
    private RateRepository rateRepository;
    private MutableLiveData<PostDetailResponse> postDetailResponse;
    private MutableLiveData<RateResponse> rateResponse;
    private int commentPage;
    private long id;
    private Context context;
    private String accessToken;
    private MutableLiveData<Boolean> isExpired;

    public void setContext(Context context) {
        this.context = context;
    }

    public BlogDetailViewModel(String accessToken, long id){
        this.id = id;
        rateRepository = RateRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        commentPage = 1;
        this.accessToken = accessToken;
        rateResponse = rateRepository.findByBlogId(this.accessToken, this.id, this.commentPage, 6);
        isExpired = new MutableLiveData<>();
        isExpired.setValue(false);
    }

    public int getCommentPage() {
        return commentPage;
    }

    public void setCommentPage(int commentPage) {
        this.commentPage = commentPage;
        rateRepository.findByBlogId(accessToken, id, commentPage, 6).observeForever(data -> {
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

    public MutableLiveData<Boolean> getIsExpired() {
        return isExpired;
    }

    public MutableLiveData<RateResponse> getRateResponse() {
        return rateResponse;
    }
    public void addRate(String accessToken, AddRateRequest request){
        Toast.makeText(context, accessToken, Toast.LENGTH_SHORT).show();
        rateRepository.addRate(accessToken, request, new ActionCallback<Rate>() {
            @Override
            public void onSuccess(Rate result) {
                rateRepository.findByBlogId(accessToken, id, commentPage, 6).observeForever(rates -> {
                    rateResponse.setValue(rates);
                });
            }
            @Override
            public void onFailure(Integer status, String message) {
                if(status.equals(AuthInstance.UNAUTHORIZED_CODE)){
                    isExpired.setValue(true);
                }

                else{
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
