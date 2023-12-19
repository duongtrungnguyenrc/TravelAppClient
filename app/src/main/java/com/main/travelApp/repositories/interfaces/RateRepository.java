package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.Rate;
import com.main.travelApp.response.RateResponse;

import java.util.List;

public interface RateRepository {
    public MutableLiveData<RateResponse> findByBlogId(long id, int page, int limit);
    public MutableLiveData<RateResponse> findByTourId(long id, int page, int limit);
}
