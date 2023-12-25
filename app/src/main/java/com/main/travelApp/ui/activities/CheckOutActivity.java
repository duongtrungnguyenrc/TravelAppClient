package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        repository.getOrder(getIntent().getExtras().getString("orderId")).observe(this, data -> {
            binding.textView8.setText("Thông tin chuyến đi đã được gửi đến " +
                    data.getContactInfo().getCustomerEmail() +
                    ". hãy kiểm tra nhé!");
            binding.txtOrderDate.setText(data.getOrderDate());
            binding.txtMethod.setText(data.getPaymentMethod());
            binding.txtTotal.setText(data.getTotalPrice() + " VND");
            binding.txtStatus.setText(data.getStatus());
            binding.txtTourName.setText(data.getTour().getName());
            binding.txtAdults.setText(data.getAdults() + "");
            binding.txtChildren.setText(data.getChildren() + "");
            binding.tourType.setText(data.getTour().getTypeTitle());
            binding.txtDepartDate.setText(data.getDepartDate());
            binding.txtEndDate.setText(data.getEndDate());
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