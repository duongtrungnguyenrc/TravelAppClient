package com.main.travelApp.repositories.impls;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.repositories.interfaces.OrderRepository;
import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IOrderService;
import com.main.travelApp.services.api.IPostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepositoryImpl implements OrderRepository {
    private IOrderService orderService;
    private static OrderRepositoryImpl instance;
    private OrderRepositoryImpl(){
        this.orderService = APIClient.getClient().create(IOrderService.class);
    }

    public static OrderRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new OrderRepositoryImpl();
    }

    @Override
    public MutableLiveData<String> CreateOrder(CreateOrderRequest payload, ActionCallback<String> action) {
        MutableLiveData<String> result = new MutableLiveData<>();
        orderService.createOrder(payload).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                if(response.isSuccessful() && response.body().getData() != null) {
                    action.onSuccess(response.body().getData());
                    result.setValue(response.body().getData());
                }
                else {
                    action.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                action.onFailure(t.getMessage());
            }
        });
        return result;
    }

    @Override
    public LiveData<Order> getOrder(String id) {
        MutableLiveData<Order> order = new MutableLiveData<>();
        Call<BaseResponse<Order>> call = orderService.getOrder(id);
        call.enqueue(new Callback<BaseResponse<Order>>() {
            @Override
            public void onResponse(Call<BaseResponse<Order>> call, Response<BaseResponse<Order>> response) {
                if(response.isSuccessful()){
                    order.setValue(response.body().getData());
                }else{
                    order.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Order>> call, Throwable t) {
                order.setValue(null);
            }
        });
        return order;
    }
}
