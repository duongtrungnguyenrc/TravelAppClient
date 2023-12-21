package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.response.SearchResponse;

public interface SearchRepository {
    void search(String keyword, ActionCallback<SearchResponse> action);
}
