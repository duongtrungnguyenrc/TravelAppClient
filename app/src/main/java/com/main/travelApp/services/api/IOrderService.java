package com.main.travelApp.services.api;

import com.main.travelApp.models.Order;
import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IOrderService {
    @POST("payment/app/create_payment")
    Call<BaseResponse<String>> createOrder(@Body CreateOrderRequest payload);

    @GET("order/{id}")
    Call<BaseResponse<Order>> findOrderById(@Path("id") Long id);
}
