package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.Place;
import com.main.travelApp.models.Tour;
import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.TourDateResponse;

import java.util.List;

public interface TourRepository {
    public MutableLiveData<AllTourResponse> findAll(int page, int limit);
    public LiveData<List<Place>> findTopDestination();
    public LiveData<Tour> find(long id);
    public MutableLiveData<TourDateResponse> findAllDates(long id);
}
