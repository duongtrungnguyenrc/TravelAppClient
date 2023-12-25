package com.main.travelApp.viewmodels;

import android.app.AlertDialog;
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
import com.main.travelApp.request.UpdateRateRequest;
import com.main.travelApp.response.PostDetailResponse;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.ui.components.ExpiredDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;

public class BlogDetailViewModel extends ViewModel {
    private PostRepository postRepository;
    private RateRepository rateRepository;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<PostDetailResponse> postDetailResponse;
    private MutableLiveData<RateResponse> rateResponse;
    private int commentPage;
    private long id;
    private Context context;
    private String accessToken;
    private MutableLiveData<Boolean> isExpired;
    private MutableLiveData<Boolean> isViewRisen;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
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
        isViewRisen = new MutableLiveData<>();
        isViewRisen.setValue(false);
    }

    public MutableLiveData<Boolean> getIsViewRisen() {
        return isViewRisen;
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
        rateRepository.addRate(accessToken, request, new ActionCallback<Rate>() {
            @Override
            public void onSuccess(Rate result) {
                Toast.makeText(context, "Thêm bình luận thành công!", Toast.LENGTH_SHORT).show();
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

    public void updateRate(UpdateRateRequest request, AlertDialog dialog){
        rateRepository.updateRate(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), request, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                rateRepository.findByBlogId(accessToken, id, commentPage, 6).observeForever(rates -> {
                    rateResponse.setValue(rates);
                });
                dialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteRate(Long rateId){
        rateRepository.deleteRate(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), rateId, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                rateRepository.findByBlogId(accessToken, id, commentPage, 6).observeForever(rates -> {
                    rateResponse.setValue(rates);
                });
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addView(){
        postRepository.addPostView(id, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                isViewRisen.setValue(true);
            }

            @Override
            public void onFailure(String message) {
                isViewRisen.setValue(false);
            }
        });
    }
}
