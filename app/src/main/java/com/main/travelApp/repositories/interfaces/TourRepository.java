package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;

import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.models.Place;

import java.util.List;

public interface TourRepository {
    public LiveData<List<GeneralTour>> findAll(int page, int limit);
    public LiveData<List<Place>> findTopDestination();
}
