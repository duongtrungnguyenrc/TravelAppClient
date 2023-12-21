package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.main.travelApp.R;
import com.main.travelApp.adapters.DateAdapter;
import com.main.travelApp.adapters.HotelAdapter;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.callbacks.BottomSheetActionHandler;
import com.main.travelApp.callbacks.OnRecyclerViewItemClickListener;
import com.main.travelApp.databinding.ActivitySelectTicketBinding;
import com.main.travelApp.models.TourDate;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.viewmodels.TourDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class SelectTicketActivity extends AppCompatActivity {
    private ActivitySelectTicketBinding binding;
    private BottomSheet datePickerBottomSheet;
    private long selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySelectTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ScreenManager.enableFullScreen(getWindow());
        init();
    }

    private void init() {
        TourDetailViewModel viewModel = new ViewModelProvider(this).get(TourDetailViewModel.class);
        this.datePickerBottomSheet = new BottomSheet(this, getLayoutInflater(), R.layout.frame_date_picker, "Chọn ngày");

        Intent intent = getIntent();
        long tourId = intent.getLongExtra("tour-id", -1);
        selectedId = intent.getLongExtra("date-id", -1);


        binding.layoutOpenCalendar.setOnClickListener(view -> {
            showDatePickerBottomSheet();
        });

        binding.btnBack.setOnClickListener(view -> finish());

        if(tourId != -1) {
            viewModel.getDates(tourId).observe(this, res -> {
                Log.d("test", res.toString());
                DateAdapter dateAdapter = new DateAdapter(res.getTourDates(), this);
                HotelAdapter hotelAdapter = new HotelAdapter(res.getTourHotels(), this);
                dateAdapter.setSelectedId(selectedId);

                dateAdapter.setOnDateSelectedChange((date, position) -> {
                    selectedId = date.getId();
                    binding.txtSelectedDate.setText(date.getDepartDate());
                });

                binding.rcvDates.setAdapter(dateAdapter);
                binding.rcvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvHotels.setAdapter(hotelAdapter);
                binding.rcvHotels.setLayoutManager(new LinearLayoutManager(this));

                Picasso.get()
                        .load(res.getTourImage())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgThumbnail);
                binding.txtTourName.setText(res.getTourName());

                TourDate selectedDate = findDateById(res.getTourDates(), selectedId);
                if(selectedDate != null) {
                    binding.txtSelectedDate.setText(selectedDate.getDepartDate());
                }
                else {
                    finish();
                }
            });
        }
    }

    private TourDate findDateById(List<TourDate> tourDateList, long id) {
        return tourDateList.stream()
                .filter(date -> date.getId() == id)
                .findFirst().orElse(null);
    }

    private void showDatePickerBottomSheet() {
        this.datePickerBottomSheet.show();
    }
}