package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.Tour;
import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.RateResponse;

public class TourDetailViewModel extends ViewModel {
    private final TourRepository tourRepository;
    private final RateRepository rateRepository;

    private MutableLiveData<RateResponse> rateResponse;
    private int ratingPage = 1;

    public TourDetailViewModel() {
        this.tourRepository = TourRepositoryImpl.getInstance();
        this.rateRepository = RateRepositoryImpl.getInstance();
    }

    public LiveData<Tour> getTour(int id) {
        return tourRepository.find(id);
    }

    public MutableLiveData<RateResponse> getRateResponse(long id) {
        this.rateResponse = rateRepository.findByTourId(id, ratingPage, 10);
        return rateResponse;
    }

    public int getRatingPage() {
        return ratingPage;
    }

    public void setRatingPage(int commentPage, long id) {
        this.ratingPage = commentPage;
        rateRepository.findByBlogId(id, commentPage, 6).observeForever(data -> {
            rateResponse.setValue(data);
        });
    }
}
