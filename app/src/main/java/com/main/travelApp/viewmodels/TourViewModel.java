package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.repositories.impls.TourRepositoryImpl;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.AllTourResponse;

import java.util.List;

public class TourViewModel extends ViewModel {
    private TourRepository tourRepository;
    private MutableLiveData<AllTourResponse> tours;
    private int page;
    private int limit;

    public TourViewModel(){
        page = 1;
        limit = 15;
        tourRepository = TourRepositoryImpl.getInstance();
        tours = tourRepository.findAll(page, limit);
    }

    public LiveData<AllTourResponse> getTours() {
        return tours;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        tourRepository.findAll(this.page, this.limit).observeForever(result -> {
            tours.setValue(result);
        });
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
