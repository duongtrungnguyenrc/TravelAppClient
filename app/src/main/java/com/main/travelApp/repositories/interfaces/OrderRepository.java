package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.request.CreateOrderRequest;

public interface OrderRepository {
    public MutableLiveData<String> CreateOrder(CreateOrderRequest payload, ActionCallback<String> action);
}
