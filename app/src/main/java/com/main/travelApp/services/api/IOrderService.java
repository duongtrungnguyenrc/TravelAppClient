package com.main.travelApp.services.api;

import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IOrderService {
    @POST("payment/app/create_payment")
    Call<BaseResponse<String>> createOrder(@Body CreateOrderRequest payload);
}
