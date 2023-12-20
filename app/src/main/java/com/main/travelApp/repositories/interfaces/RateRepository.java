package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Rate;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.response.AddRateResponse;
import com.main.travelApp.response.RateResponse;

import java.util.List;

public interface RateRepository {
    public MutableLiveData<RateResponse> findByBlogId(String accessToken, long id, int page, int limit);
    public MutableLiveData<RateResponse> findByTourId(long id, int page, int limit);
    public void addRate(String accessToken, AddRateRequest request, ActionCallback<Rate> callback);
}
