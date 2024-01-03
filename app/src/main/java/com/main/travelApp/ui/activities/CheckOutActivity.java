package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.main.travelApp.R;
import com.main.travelApp.databinding.ActivityCheckOutBinding;
import com.main.travelApp.repositories.impls.OrderRepositoryImpl;
import com.main.travelApp.utils.ScreenManager;
import com.squareup.picasso.Picasso;

public class CheckOutActivity extends AppCompatActivity {

    private ActivityCheckOutBinding binding;
    private OrderRepositoryImpl repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());
        repository = OrderRepositoryImpl.getInstance();

        binding.btnBack.setOnClickListener(view -> onBackPressed());
        Intent intent = getIntent();
        if(intent != null){
            String orderId = intent.getStringExtra("orderId");
            String statusStr = intent.getStringExtra("status");
            Boolean status = Boolean.valueOf(statusStr);

            Log.d("TEST_TAG", "onCreate: " + orderId + status);
            if(!status) {
                binding.txtNotify.setText("Bạn đã hủy đặt tour này, vui lòng kiểm tra email để xem chi tiết");
                binding.successCheckmark.setVisibility(View.INVISIBLE);
                binding.failedCheckmark.setVisibility(View.VISIBLE);
                binding.txtPaymentStatus.setText("Giao dịch thất bại");
                binding.statusBackground.setBackgroundResource(com.google.android.material.R.color.design_default_color_error);
            }else {
                binding.successCheckmark.setVisibility(View.VISIBLE);
                binding.failedCheckmark.setVisibility(View.INVISIBLE);
                binding.txtPaymentStatus.setText("Giao dịch thành công");
                binding.statusBackground.setBackgroundResource(R.color.colorPrimary);
            }

            repository.getOrder(orderId).observe(this, data -> {
                binding.txtPaymentTime.setText(data.getOrderDate());
                if(status)
                    binding.txtNotify.setText("Thông tin chuyến đi đã được gửi đến " +
                            data.getContactInfo().getCustomerEmail() +
                            ". hãy kiểm tra nhé!");
                binding.txtPaymentMethod.setText(data.getPaymentMethod());
                binding.txtTotalPrice.setText(data.getTotalPrice() + " VND");
                binding.txtStatus.setText(data.getStatus());
                switch (data.getStatus()){
                    case "Đã hủy" -> binding.txtStatus.setTextColor(getResources().getColor(R.color.vertical_stepper_form_text_color_error_message));
                    case "Đã thanh toán" -> binding.txtStatus.setTextColor(getResources().getColor(R.color.green));
                    case "Đang chờ" -> getResources().getColor(R.color.colorPrimary);
                }
                binding.txtTourName.setText(data.getTour().getName());
                binding.txtAdults.setText(data.getAdults() + "");
                binding.txtChildren.setText(data.getChildren() + "");
                binding.txtTicketRank.setText(data.getTour().getTypeTitle());
                binding.txtStartDate.setText(data.getDepartDate());
                binding.txtEndDate.setText(data.getEndDate());
                binding.txtSpecialRequest.setText(data.getSpecialRequest());
                if(data.getHotel() != null){
                    if(data.getHotel().getIllustration() != null && !data.getHotel().getIllustration().isEmpty()){
                        Picasso.get()
                                .load(data.getHotel().getIllustration())
                                .placeholder(R.color.light_gray)
                                .error(R.color.light_gray)
                                .into(binding.imgHotelThumbnail);
                    }
                    binding.txtHotelName.setText(data.getHotel().getName());
                    binding.txtHotelAddress.setText(data.getHotel().getAddress());
                }else{
                    binding.txtHotelAddress.setVisibility(View.GONE);
                    binding.txtHotelName.setVisibility(View.GONE);
                    binding.imgHotelThumbnail.setVisibility(View.GONE);
                }
            });
        }
    }
}