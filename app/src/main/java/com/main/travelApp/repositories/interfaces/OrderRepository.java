package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.request.CreateOrderRequest;

public interface OrderRepository {
    public MutableLiveData<String> createOrder(CreateOrderRequest payload, ActionCallback<String> action);

    public MutableLiveData<Order> findOrderById(Long id);
}
