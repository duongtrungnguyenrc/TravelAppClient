package com.main.travelApp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.response.RateDetailResponse;
import com.main.travelApp.response.RateResponse;

public class RatingViewModel extends ViewModel {
    private final RateRepositoryImpl rateRepository;

    private MutableLiveData<RateDetailResponse> rateResponse;

    private int ratingPage = 1;

    public RatingViewModel() {
        rateRepository = RateRepositoryImpl.getInstance();
    }

    public MutableLiveData<RateDetailResponse> getRateResponse(long id) {
        this.rateResponse = rateRepository.findByTourId(id, ratingPage, 100);
        return rateResponse;
    }

    public int getRatingPage() {
        return ratingPage;
    }

    public void setRatingPage(int commentPage, long id) {

    }
}
