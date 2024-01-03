package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.request.CreateOrderRequest;

public interface OrderRepository {
    public MutableLiveData<String> createOrder(String header, CreateOrderRequest payload, ActionCallback<String> action);
    public LiveData<Order> getOrder(String id);
}
