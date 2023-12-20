package com.main.travelApp.repositories.impls;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Rate;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.response.AddRateResponse;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.RateDetailResponse;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IRateService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateRepositoryImpl implements RateRepository {
    private IRateService rateService;
    private static RateRepositoryImpl instance;
    private RateRepositoryImpl(){
        rateService = APIClient.getClient().create(IRateService.class);
    }
    public static RateRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new RateRepositoryImpl();
    }
    @Override
    public MutableLiveData<RateResponse> findByBlogId(String accessToken, long id, int page, int limit) {
        MutableLiveData<RateResponse> rateResponse = new MutableLiveData<>();
        Call<BaseResponse<RateResponse>> call = rateService.getRateByBlogId(accessToken, id, page, limit);
        call.enqueue(new Callback<BaseResponse<RateResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<RateResponse>> call, Response<BaseResponse<RateResponse>> response) {
                if(response.isSuccessful()){
                    rateResponse.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RateResponse>> call, Throwable t) {
                rateResponse.setValue(null);
            }
        });

        return rateResponse;
    }

    @Override
    public MutableLiveData<RateDetailResponse> findByTourId(long id, int page, int limit) {
        MutableLiveData<RateDetailResponse> rateResponse = new MutableLiveData<>();
        Call<BaseResponse<RateDetailResponse>> call = rateService.getRateByTourId(id, page, limit);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<RateDetailResponse>> call, Response<BaseResponse<RateDetailResponse>> response) {
                if (response.isSuccessful()) {
                    rateResponse.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RateDetailResponse>> call, Throwable t) {
                rateResponse.setValue(null);
            }
        });


        return rateResponse;
    }

    @Override
    public void addRate(String accessToken, AddRateRequest request, ActionCallback<Rate> callback) {
        Call<BaseResponse<AddRateResponse>> call = rateService.addRate(accessToken, request);
        call.enqueue(new Callback<BaseResponse<AddRateResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<AddRateResponse>> call, Response<BaseResponse<AddRateResponse>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body().getData().getRateAdded());
                }else{
                    callback.onFailure(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<AddRateResponse>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }
}
