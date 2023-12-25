package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.main.travelApp.R;
import com.main.travelApp.adapters.DateAdapter;
import com.main.travelApp.adapters.HotelAdapter;
import com.main.travelApp.callbacks.OnRecyclerViewHasNestedItemClickListener;
import com.main.travelApp.callbacks.OnRecyclerViewItemClickListener;
import com.main.travelApp.databinding.ActivitySelectTicketBinding;
import com.main.travelApp.models.Hotel;
import com.main.travelApp.models.HotelRoom;
import com.main.travelApp.models.Tour;
import com.main.travelApp.models.TourDate;
import com.main.travelApp.request.CreateOrderRequest;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.viewmodels.TourDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SelectTicketActivity extends AppCompatActivity {
    private ActivitySelectTicketBinding binding;
    private BottomSheet datePickerBottomSheet;
    private long selectedId;
    private CreateOrderRequest newPaymentPayload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySelectTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ScreenManager.enableFullScreen(getWindow());
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        Intent intent = getIntent();
        Tour tour = new Gson().fromJson(intent.getStringExtra("tour"), Tour.class);
        selectedId = intent.getLongExtra("date-id", -1);

        TourDetailViewModel viewModel = new ViewModelProvider(this).get(TourDetailViewModel.class);
        this.datePickerBottomSheet = new BottomSheet(this, getLayoutInflater(), R.layout.frame_date_picker, "Chọn ngày");
        newPaymentPayload = new CreateOrderRequest();

        DateAdapter dateAdapter = new DateAdapter(this);
        dateAdapter.setSelectedId(selectedId);
        dateAdapter.setOnDateSelectedChange((date, position) -> {
            selectedId = date.getId();
            newPaymentPayload.setTourDateId(date.getId());
            binding.txtSelectedDate.setText(date.getDepartDate());
        });

        HotelAdapter hotelAdapter = new HotelAdapter(this);
        hotelAdapter.setItemClickListener(new OnRecyclerViewHasNestedItemClickListener<Hotel, HotelRoom>() {
            @Override
            public void onClick(Hotel item, long position) {
                newPaymentPayload.setHotelId(item.getId());
            }

            @Override
            public void onBlur(Hotel item, HotelRoom nestedItem) {
                newPaymentPayload.setHotelId(null);
                newPaymentPayload.setRoomType("");
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() - nestedItem.getPrice());
                updateTotalPrice();
            }
        } );
        hotelAdapter.setNestedItemClickListener(new OnRecyclerViewItemClickListener<>() {
            @Override
            public void onClick(HotelRoom item, long position) {
                newPaymentPayload.setRoomType(item.getType());
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() + item.getPrice());
                updateTotalPrice();
            }

            @Override
            public void onBlur(HotelRoom item) {
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() - item.getPrice());
                newPaymentPayload.setRoomType("");
                updateTotalPrice();
            }
        });
        binding.rcvDates.setAdapter(dateAdapter);
        binding.rcvDates.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rcvHotels.setAdapter(hotelAdapter);
        binding.rcvHotels.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        if(tour != null) {
            viewModel.getDates(tour.getId()).observe(this, res -> {
                dateAdapter.setTourDates(res.getTourDates());
                hotelAdapter.setHotels(res.getTourHotels());
                TourDate selectedDate = findDateById(res.getTourDates(), selectedId);

                newPaymentPayload.setTourDateId(selectedDate.getId());
                newPaymentPayload.setAdultPrice(selectedDate.getAdultPrice());
                newPaymentPayload.setChildPrice(selectedDate.getChildPrice());

                Picasso.get()
                        .load(res.getTourImage())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgThumbnail);
                binding.txtTourName.setText(res.getTourName());
                binding.txtAdultPrice.setText(selectedDate.getStringAdultPrice());
                binding.txtChildPrice.setText(selectedDate.getStringChildPrice());

                binding.layoutOpenCalendar.setOnClickListener(view -> {
                    showDatePickerBottomSheet();
                });

                binding.btnBack.setOnClickListener(view -> finish());

                binding.btnIncreaseAdult.setOnClickListener(view -> {
                    newPaymentPayload.increaseAdults();
                    binding.txtAdultQuantity.setText(String.valueOf(newPaymentPayload.getAdults()));
                    updateTotalPrice();
                });
                binding.btnDecreaseAdult.setOnClickListener(view -> {
                    newPaymentPayload.decreaseAdults();
                    binding.txtAdultQuantity.setText(String.valueOf(newPaymentPayload.getAdults()));
                    updateTotalPrice();
                });

                binding.btnIncreaseChild.setOnClickListener(view -> {
                    newPaymentPayload.increaseChilds();
                    binding.txtChildQuantity.setText(String.valueOf(newPaymentPayload.getChildren()));
                    updateTotalPrice();
                });

                binding.btnDecreaseChild.setOnClickListener(view -> {
                    newPaymentPayload.decreaseChilds();
                    binding.txtChildQuantity.setText(String.valueOf(newPaymentPayload.getChildren()));
                    updateTotalPrice();
                });

                binding.btnCreateOrder.setOnClickListener(view -> {
                    if(newPaymentPayload.getAdults() == 0 && newPaymentPayload.getChildren() == 0) {
                        Toast.makeText(this, "Vui lòng chọn số lượng vé!", Toast.LENGTH_SHORT).show();
                    } else if (newPaymentPayload.getHotelId() != null && newPaymentPayload.getRoomType().isEmpty()) {
                        Toast.makeText(this, "Vui lòng chọn loại phòng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent confirmOrderIntent = new Intent(this, ConfirmOrderActivity.class);
                        confirmOrderIntent.putExtra("tour", new Gson().toJson(tour));
                        confirmOrderIntent.putExtra("tour-date", new Gson().toJson(selectedDate));
                        confirmOrderIntent.putExtra("payload", new Gson().toJson(newPaymentPayload));
                        startActivity(confirmOrderIntent);
                    }
                });

                binding.txtSelectedDate.setText(selectedDate.getDepartDate());
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

    private void updateTotalPrice() {
        binding.txtTotalPrice.setText(newPaymentPayload.getStringTotalPrice());
    }
}