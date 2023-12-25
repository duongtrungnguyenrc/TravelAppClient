package com.main.travelApp.repositories.impls;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.Place;
import com.main.travelApp.models.Tour;
import com.main.travelApp.request.TourFilterRequest;
import com.main.travelApp.response.AllTourResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.repositories.interfaces.TourRepository;
import com.main.travelApp.response.TourDateResponse;
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
    public MutableLiveData<AllTourResponse> findAll(int page, int limit) {
        MutableLiveData<AllTourResponse> tourRepsonse = new MutableLiveData<>();
        Call<BaseResponse<AllTourResponse>> call = tourService.getAllTours(page, limit);
        call.enqueue(new Callback<BaseResponse<AllTourResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AllTourResponse>> call, Response<BaseResponse<AllTourResponse>> response) {
                if(response.isSuccessful() && response != null){
                    tourRepsonse.setValue(response.body().getData());
                }else{
                    Log.d("TOUR_findAll", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AllTourResponse>> call, Throwable t) {
                t.printStackTrace();
                tourRepsonse.setValue(null);
            }
        });
        return tourRepsonse;
    }

    @Override
    public LiveData<List<Place>> findTopDestination() {
        MutableLiveData<List<Place>> places = new MutableLiveData<>();
        Call<BaseResponse<List<Place>>> call = tourService.getTopDestination();
        call.enqueue(new Callback<BaseResponse<List<Place>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Place>>> call, Response<BaseResponse<List<Place>>> response) {
                Log.d("TOUR_findTopDestination", "onResponse: " + response.message());
                if(response.isSuccessful() && response != null){
                    places.setValue(response.body().getData());
                }else{
                    places.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Place>>> call, Throwable t) {
                t.printStackTrace();
                places.setValue(null);
            }
        });
        return places;
    }

    @Override
    public LiveData<Tour> find(long id) {
        MutableLiveData<Tour> tour = new MutableLiveData<>();
        Call<BaseResponse<Tour>> call = tourService.get(id);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<Tour>> call, Response<BaseResponse<Tour>> response) {
                if (response.isSuccessful()) {
                    tour.setValue(response.body().getData());
                } else {
                    tour.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Tour>> call, Throwable t) {
                t.printStackTrace();
                tour.setValue(null);
            }
        });

        return tour;
    }

    @Override
    public MutableLiveData<TourDateResponse> findAllDates(long id) {
        MutableLiveData<TourDateResponse> dates = new MutableLiveData<>();
        Call<BaseResponse<TourDateResponse>> call = tourService.getDates(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<TourDateResponse>> call, Response<BaseResponse<TourDateResponse>> response) {
                if (response.isSuccessful()) {
                    dates.setValue(response.body().getData());
                } else {
                    dates.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<TourDateResponse>> call, Throwable t) {
                t.printStackTrace();
                dates.setValue(null);
            }
        });

        return dates;
    }

    @Override
    public MutableLiveData<AllTourResponse> findByFilter(TourFilterRequest request) {
        MutableLiveData<AllTourResponse> tourRepsonse = new MutableLiveData<>();
        Call<BaseResponse<AllTourResponse>> call = tourService.findTourByFilter(request);
        call.enqueue(new Callback<BaseResponse<AllTourResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AllTourResponse>> call, Response<BaseResponse<AllTourResponse>> response) {
                if(response.isSuccessful() && response != null){
                    tourRepsonse.setValue(response.body().getData());
                }else{
                    Log.d("TOUR_findAll", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AllTourResponse>> call, Throwable t) {
                t.printStackTrace();
                tourRepsonse.setValue(null);
            }
        });
        return tourRepsonse;
    }
}
