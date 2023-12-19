package com.main.travelApp.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.models.Place;
import com.main.travelApp.repositories.impls.PostRepositoryImpl;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.interfaces.PostRepository;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.AllTourResponse;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private TourRepository tourRepository;
    private PostRepository postRepository;
    private LiveData<AllTourResponse> tours;
    private LiveData<List<GeneralPost>> posts;
    private LiveData<List<Place>> places;
    private Application application;

    public HomeViewModel(){
        tourRepository = TourRepositoryImpl.getInstance();
        postRepository = PostRepositoryImpl.getInstance();
        tours = tourRepository.findAll(1, 10);
        posts = postRepository.findNewestPosts();
        places = tourRepository.findTopDestination();
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
}
