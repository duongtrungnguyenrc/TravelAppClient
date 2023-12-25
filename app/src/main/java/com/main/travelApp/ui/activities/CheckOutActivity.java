package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityCheckOutBinding;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.viewmodels.CheckoutViewModel;

import java.util.Date;

import lombok.Data;

public class CheckOutActivity extends AppCompatActivity {

    private ActivityCheckOutBinding binding;
    private CheckoutViewModel checkoutViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());
        init();
    }

    private void init() {
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        Intent intent = getIntent();
//
//        checkoutViewModel.getOrder(1L).observe(this, order -> {
//            binding.txtPaymentTime.setText(order.getOrderDate());
//            binding.txtPaymentMethod.setText(order.getPaymentMethod());
//            binding.txtPaymentStatus.setText(order.getStatus());
//            binding.txtTourName.setText(order.getTour().getName());
//            binding.txtAdults.setText(order.getAdults());
//            binding.txtChild.setText(order.getChildren());
//            binding.txtTicketRank.setText(order.getTour().getType());
//            binding.txtStartDate.setText(order.getDepartDate());
//            binding.txtEndDate.setText(order.getEndDate());
//            binding.txtHotelName.setText(order.getHotel().getName());
//            binding.txtHotelAddress.setText(order.getHotel().getAddress());
//            binding.txtSpecialRequest.setText(order.getSpecialRequest());
//        });
    }
}