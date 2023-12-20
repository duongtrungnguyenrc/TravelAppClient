package com.main.travelApp.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.models.Place;
import com.main.travelApp.models.User;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private TourRepository tourRepository;
    private AuthRepository authRepository;
    private PostRepository postRepository;
    private LiveData<AllTourResponse> tours;
    private LiveData<List<GeneralPost>> posts;
    private LiveData<List<Place>> places;
    private Application application;
    private User currentUser;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<Boolean> isExpired;

    public HomeViewModel(SharedPreferences sharedPreferences){
        tourRepository = TourRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        authRepository = AuthRepositoryImpl.getInstance();
        tours = tourRepository.findAll(1, 10);
        posts = postRepository.findNewestPosts();
        places = tourRepository.findTopDestination();
        this.sharedPreferences = sharedPreferences;
        bindCurrentUser();

        isExpired = new MutableLiveData<>();
        isExpired.setValue(false);
    }

    public void setApplication(Application application){
        this.application = application;
    }
    public LiveData<AllTourResponse> getTours(){
        return tours;
    }
    public LiveData<List<GeneralPost>> getNewestPosts(){
        return posts;
    }
    public LiveData<List<Place>> getPlaces(){
        return places;
    }
    private void bindCurrentUser(){
        currentUser = new User();
        currentUser.setFullName(sharedPreferences.getString(SharedPreferenceKeys.USER_FULL_NAME, ""));
        currentUser.setAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_AVATAR, ""));
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public MutableLiveData<Boolean> getIsExpired() {
        return isExpired;
    }
    public void verifyUser(String accessToken){
        authRepository.verifyToken(accessToken, new ActionCallback<Object>() {
            @Override
            public void onFailure(Integer status, String message) {
                isExpired.setValue(true);
            }
        });
    }
}
