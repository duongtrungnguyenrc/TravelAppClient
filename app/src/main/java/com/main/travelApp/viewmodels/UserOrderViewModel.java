package com.main.travelApp.viewmodels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.models.Order;
import com.main.travelApp.repositories.impls.UserRepositoryImpl;
import com.main.travelApp.repositories.interfaces.UserRepository;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.SharedPreferenceKeys;

import java.util.List;

public class UserOrderViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<List<Order>> orders;
    private SharedPreferences sharedPreferences;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public UserOrderViewModel(){
        userRepository = UserRepositoryImpl.getInstance();
    }

    public LiveData<List<Order>> getOrders() {
        return orders = userRepository.getOrders(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""));
    }

    public void cancelOrder(long id, AlertDialog parentDialog, AlertDialog childDialog){
        String accessToken = sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, "");
        userRepository.cancelOrder(accessToken, id, new ActionCallback<String>() {
            @Override
            public void onSuccess(String result) {
                userRepository.getOrders(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, "")).observeForever(results -> {
                    orders.setValue(results);
                });
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                childDialog.dismiss();
                parentDialog.dismiss();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                childDialog.dismiss();
            }
        });
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }
}
