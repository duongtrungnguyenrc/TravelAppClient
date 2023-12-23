package com.main.travelApp.repositories.impls;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.models.User;
import com.main.travelApp.repositories.interfaces.UserRepository;
import com.main.travelApp.request.ActivitiesRecordRequest;
import com.main.travelApp.request.UpdateUserRequest;
import com.main.travelApp.response.BaseResponse;
import com.main.travelApp.models.LoginHistory;
import com.main.travelApp.response.RecentActivitiesResponse;
import com.main.travelApp.services.api.APIClient;
import com.main.travelApp.services.api.IUserService;
import com.main.travelApp.utils.ErrorResponseHandler;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {
    private IUserService userService;
    private UserRepositoryImpl(){
        userService = APIClient.getClient().create(IUserService.class);
    }
    private static UserRepositoryImpl instance;
    public static UserRepositoryImpl getInstance(){
        if(instance != null)
            return instance;
        return new UserRepositoryImpl();
    }
    @Override
    public void update(String accessToken, UpdateUserRequest request, ActionCallback<BaseResponse<Object>> callback) {
        Call<BaseResponse<Object>> call = userService.updateUser(accessToken, request);
        call.enqueue(new Callback<BaseResponse<Object>>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else{
                    callback.onFailure("Không tìm thấy tài khoản người dùng!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public LiveData<List<LoginHistory>> getLoginHistory(String accessToken) {
        MutableLiveData<List<LoginHistory>> loginHistory = new MutableLiveData<>();
        Call<BaseResponse<List<LoginHistory>>> call = userService.getUserLoginHistory(accessToken);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<List<LoginHistory>>> call, Response<BaseResponse<List<LoginHistory>>> response) {
                if (response.isSuccessful()) {
                    loginHistory.setValue(response.body().getData());
                } else {
                    loginHistory.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<LoginHistory>>> call, Throwable t) {
                loginHistory.setValue(null);
            }
        });
        return loginHistory;
    }

    @Override
    public MutableLiveData<List<Order>> getOrders(String accessToken) {
        MutableLiveData<List<Order>> orders = new MutableLiveData<>();
        Call<BaseResponse<List<Order>>> call = userService.getUserOrders(accessToken);
        call.enqueue(new Callback<BaseResponse<List<Order>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Order>>> call, Response<BaseResponse<List<Order>>> response) {
                if(response.isSuccessful()){
                    orders.setValue(response.body().getData());
                }else{
                    orders.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Order>>> call, Throwable t) {
                orders.setValue(null);
            }
        });
        return orders;
    }

    @Override
    public void cancelOrder(String accessToken, long id, ActionCallback<String> callback) {
        Call<BaseResponse<Object>> call = userService.cancelOrder(accessToken, id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getMessage());
                } else {
                    ErrorResponseHandler<BaseResponse<Object>> handler = new ErrorResponseHandler<>();
                    BaseResponse<Object> errorResponse = handler.getResponseBody(response);
                    callback.onFailure(errorResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void updateAvatar(String accessToken, MultipartBody.Part image, ActionCallback<String> callback) {
        Call<BaseResponse<User>> call = userService.updateAvatar(accessToken, image);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body().getData().getAvatar());
                } else {
                    callback.onFailure("Không tìm thấy người dùng!");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                callback.onFailure("Có lỗi xảy ra, vui lòng thử lại");
                t.printStackTrace();
            }
        });
    }

    @Override
    public MutableLiveData<RecentActivitiesResponse> getRecentActivities(String accessToken, ActionCallback<RecentActivitiesResponse> callback) {
        MutableLiveData<RecentActivitiesResponse> recentActivities = new MutableLiveData<>();
        Call<BaseResponse<RecentActivitiesResponse>> call = userService.getRecentActivities(accessToken);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<RecentActivitiesResponse>> call, Response<BaseResponse<RecentActivitiesResponse>> response) {
                if (response.isSuccessful()) {
                    recentActivities.setValue(response.body().getData());
                } else {
                    callback.onFailure("Có lỗi đã xảy ra");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<RecentActivitiesResponse>> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });

        return recentActivities;
    }

    @Override
    public void recordActivity(String accessToken, ActivitiesRecordRequest payload) {
        Call<BaseResponse<Object>> call = userService.recordActivity(accessToken, payload);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
