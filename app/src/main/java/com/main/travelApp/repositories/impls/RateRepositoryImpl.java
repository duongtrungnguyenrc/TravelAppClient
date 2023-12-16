package com.main.travelApp.repositories.impls;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.models.Rate;
import com.main.travelApp.repositories.interfaces.RateRepository;
import com.main.travelApp.response.BaseResponse;
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
    public MutableLiveData<RateResponse> findByBlogId(long id, int page, int limit) {
        MutableLiveData<RateResponse> rateResponse = new MutableLiveData<>();
        Call<BaseResponse<RateResponse>> call = rateService.getRateByBlogId(id, page, limit);
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
}
