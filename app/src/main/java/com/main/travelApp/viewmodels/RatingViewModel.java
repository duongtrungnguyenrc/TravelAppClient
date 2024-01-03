package com.main.travelApp.viewmodels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Rate;
import com.main.travelApp.repositories.impls.RateRepositoryImpl;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.request.UpdateRateRequest;
import com.main.travelApp.response.RateDetailResponse;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.util.List;

public class RatingViewModel extends ViewModel {
    private final RateRepositoryImpl rateRepository;
    private SharedPreferences sharedPreferences;
    private Context context;
    private MutableLiveData<RateDetailResponse> rateResponse;
    private MutableLiveData<Boolean> isRateAdded;
    private RateDetailResponse rates;

    private int ratingPage = 1;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setRates(RateDetailResponse rates) {
        this.rates = rates;
    }

    public RateDetailResponse getRates() {
        return rates = this.rateResponse.getValue();
    }

    public RatingViewModel() {
        rateRepository = RateRepositoryImpl.getInstance();
        isRateAdded = new MutableLiveData<>();
        isRateAdded.setValue(false);
    }

    public MutableLiveData<RateDetailResponse> getRateResponse(long id) {
        this.rateResponse = rateRepository.findByTourId(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), id, ratingPage, 100);
        rates = rateResponse.getValue();
        return rateResponse;
    }

    public int getRatingPage() {
        return ratingPage;
    }

    public void setRatingPage(int commentPage, long id) {

    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public MutableLiveData<Boolean> getIsRateAdded() {
        return isRateAdded;
    }

    public void addRate(AddRateRequest request, long id){
        String accessToken = sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, "");
        rateRepository.addRate(accessToken, request, new ActionCallback<Rate>() {
            @Override
            public void onSuccess(Rate result) {
                Toast.makeText(context, "Thêm bình luận thành công!", Toast.LENGTH_SHORT).show();
                rateRepository.findByTourId(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), id, ratingPage, 100).observeForever(data -> {
                    rateResponse.setValue(data);
                });
                isRateAdded.setValue(true);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Integer status, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateRate(UpdateRateRequest request, AlertDialog dialog, long id) {
        rateRepository.updateRate(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), request, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                rateRepository.findByTourId(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), id, ratingPage, 100).observeForever(data -> {
                    rateResponse.setValue(data);
                });
                dialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteRate(long id, long tourId){
        rateRepository.deleteRate(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), id, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                rateRepository.findByTourId(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""), tourId, ratingPage, 100).observeForever(data -> {
                    rateResponse.setValue(data);
                });
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
