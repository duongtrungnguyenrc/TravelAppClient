package com.main.travelApp.utils;

import com.google.gson.Gson;
import com.main.travelApp.response.BaseResponse;

import java.io.IOException;

import retrofit2.Response;

public class ErrorResponseHandler<R> {
    public R getResponseBody(Response<R> response){
        String errorBody = "";
        try {
            errorBody = response.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        R errorResponse = (R) gson.fromJson(errorBody, BaseResponse.class);
        return errorResponse;
    }
}
