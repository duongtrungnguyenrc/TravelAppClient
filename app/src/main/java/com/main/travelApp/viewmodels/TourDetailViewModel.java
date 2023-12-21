package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.Tour;
import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.RateDetailResponse;
import com.main.travelApp.response.TourDateResponse;

public class TourDetailViewModel extends ViewModel {
    private final TourRepository tourRepository;
    private final RateRepository rateRepository;

    private MutableLiveData<RateDetailResponse> rateResponse;
    private int ratingPage = 1;

    public TourDetailViewModel() {
        this.tourRepository = TourRepositoryImpl.getInstance();
        this.rateRepository = RateRepositoryImpl.getInstance();
    }

    public LiveData<Tour> getTour(long id) {
        return tourRepository.find(id);
    }

    public MutableLiveData<RateDetailResponse> getRates(long id) {
        this.rateResponse = rateRepository.findByTourId(id, ratingPage, 10);
        return rateResponse;
    }

    public MutableLiveData<TourDateResponse> getDates(long id) {
        return tourRepository.findAllDates(id);
    }

    public int getRatingPage() {
        return ratingPage;
    }

    public void setRatingPage(int commentPage, long id) {
        this.ratingPage = commentPage;
        rateRepository.findByTourId(id, commentPage, 6).observeForever(data -> {
            rateResponse.setValue(data);
        });
    }
}
