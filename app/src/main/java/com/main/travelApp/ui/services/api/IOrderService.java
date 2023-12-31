package com.main.travelApp.ui.services.api;

import com.main.travelApp.models.Order;
import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.response.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IOrderService {
    @POST("payment/app/create_payment")
    Call<BaseResponse<String>> createOrder(@Header("Authorization") String header, @Body CreateOrderRequest payload);
    @GET("order/{id}")
    Call<BaseResponse<Order>> getOrder(@Path("id") String id);
}
