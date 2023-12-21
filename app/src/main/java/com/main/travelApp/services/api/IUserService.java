package com.main.travelApp.services.api;

import com.main.travelApp.models.Order;
import com.main.travelApp.request.UpdateUserRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.models.LoginHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUserService {
    @POST("user/update")
    Call<BaseResponse<Object>> updateUser(@Header("Authorization") String accessToken, @Body UpdateUserRequest request);
    @GET("user/login-history")
    Call<BaseResponse<List<LoginHistory>>> getUserLoginHistory(@Header("Authorization") String accessToken);
    @GET("order")
    Call<BaseResponse<List<Order>>> getUserOrders(@Header("Authorization") String accessToken);
    @POST("order/cancel/{id}")
    Call<BaseResponse<Object>> cancelOrder(@Header("Authorization") String accessToken, @Path("id") long id);
}
