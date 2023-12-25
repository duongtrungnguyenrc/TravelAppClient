package com.main.travelApp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.models.Order;
import com.main.travelApp.repositories.impls.OrderRepositoryImpl;

public class CheckoutViewModel extends ViewModel {
    private LiveData<Order> order;
    private OrderRepositoryImpl orderRepository;

    public CheckoutViewModel() {
        orderRepository = OrderRepositoryImpl.getInstance();
    }

    public LiveData<Order> getOrder(Long id) {
        return orderRepository.findOrderById(id);
    }
}
