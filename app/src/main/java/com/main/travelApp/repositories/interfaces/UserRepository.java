package com.main.travelApp.repositories.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.request.UpdateUserRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.models.LoginHistory;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Part;

public interface UserRepository {
    void update(String accessToken, UpdateUserRequest request, ActionCallback<BaseResponse<Object>> callback);
    LiveData<List<LoginHistory>> getLoginHistory(String accessToken);
    MutableLiveData<List<Order>> getOrders(String accessToken);
    void cancelOrder(String accessToken, long id, ActionCallback<String> callback);
    void updateAvatar(String accessToken, MultipartBody.Part image, ActionCallback<String> callback);
}
