package com.main.travelApp.repositories.impls;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.ITourService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourRepositoryImpl implements TourRepository {
    private ITourService tourService;
    private static TourRepositoryImpl instance;
    private TourRepositoryImpl(){
        tourService = APIClient.getClient().create(ITourService.class);
    }
    public static TourRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new TourRepositoryImpl();
    }

    @Override
    public LiveData<List<GeneralTour>> findAll(int page, int limit) {
        MutableLiveData<List<GeneralTour>> tourList = new MutableLiveData<>();
        Call<BaseResponse<AllTourResponse>> call = tourService.getAllTours(page, limit);
        call.enqueue(new Callback<BaseResponse<AllTourResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AllTourResponse>> call, Response<BaseResponse<AllTourResponse>> response) {
                if(response.isSuccessful() && response != null){
                    tourList.setValue(response.body().getData().getTours());
                }else{
                    Log.d("TOUR_findAll", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AllTourResponse>> call, Throwable t) {
                t.printStackTrace();
                tourList.setValue(null);
            }
        });
        return tourList;
    }
}