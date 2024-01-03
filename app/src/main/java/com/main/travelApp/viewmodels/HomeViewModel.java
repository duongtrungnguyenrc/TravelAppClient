package com.main.travelApp.viewmodels;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.models.Place;
import com.main.travelApp.models.User;
import com.main.travelApp.repositories.impls.AuthRepositoryImpl;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.impls.UserRepositoryImpl;
import com.main.travelApp.repositories.interfaces.AuthRepository;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.RecentActivitiesResponse;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final TourRepository tourRepository;
    private final AuthRepository authRepository;
    private final PostRepository postRepository;
    private final UserRepositoryImpl userRepository;

    private final LiveData<AllTourResponse> tours;
    private final LiveData<List<MinimizePost>> posts;
    private final LiveData<List<Place>> places;
    private User currentUser;
    private SharedPreferences sharedPreferences;
    private MutableLiveData<Boolean> isExpired;
    private LiveData<RecentActivitiesResponse> recentActivities;

    public HomeViewModel(){
        tourRepository = TourRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        authRepository = AuthRepositoryImpl.getInstance();
        userRepository = UserRepositoryImpl.getInstance();

        tours = tourRepository.findAll(1, 10);
        posts = postRepository.findNewestPosts();
        places = tourRepository.findTopDestination();
    }

    public HomeViewModel(SharedPreferences sharedPreferences){
        this();
        this.sharedPreferences = sharedPreferences;


        isExpired = new MutableLiveData<>();
        isExpired.setValue(false);
    }

    public LiveData<AllTourResponse> getTours(){
        return tours;
    }
    public LiveData<List<MinimizePost>> getNewestPosts(){
        return posts;
    }
    public LiveData<List<Place>> getPlaces(){
        return places;
    }
    private void bindCurrentUser(){
        currentUser = new User();
        currentUser.setEmail(sharedPreferences.getString(SharedPreferenceKeys.USER_EMAIL, ""));
        currentUser.setPhone(sharedPreferences.getString(SharedPreferenceKeys.USER_PHONE, ""));
        currentUser.setFullName(sharedPreferences.getString(SharedPreferenceKeys.USER_FULL_NAME, ""));
        currentUser.setAvatar(sharedPreferences.getString(SharedPreferenceKeys.USER_AVATAR, ""));
        currentUser.setAddress(sharedPreferences.getString(SharedPreferenceKeys.USER_ADDRESS, ""));
    }

    public User getCurrentUser() {
        bindCurrentUser();
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

    public LiveData<RecentActivitiesResponse> getRecentActivities() {
        return recentActivities = userRepository.getRecentActivities(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), new ActionCallback<>() {
            @Override
            public void onFailure(String message) {
                Log.e("request-error", message);
            }
        });
    }
}
