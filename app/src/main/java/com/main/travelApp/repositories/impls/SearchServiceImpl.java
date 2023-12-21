package com.main.travelApp.repositories.impls;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.repositories.interfaces.SearchRepository;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.response.SearchResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.ISearchService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchServiceImpl implements SearchRepository {

    private final ISearchService searchService;

    public SearchServiceImpl() {
        searchService = APIClient.getClient().create(ISearchService.class);
    }

    @Override
    public void search(String keyword, ActionCallback<SearchResponse> action) {
        searchService.search(keyword).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<SearchResponse>> call, Response<BaseResponse<SearchResponse>> response) {
                if(response.isSuccessful()) {
                    action.onSuccess(response.body().getData());
                }
                else {
                    try {
                        action.onFailure(response.errorBody().string());
                    } catch (IOException e) {
                        action.onError(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<SearchResponse>> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
    }
}
