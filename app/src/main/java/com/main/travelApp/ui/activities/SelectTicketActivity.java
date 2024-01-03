package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

public class SelectTicketActivity extends AppCompatActivity {
    private ActivitySelectTicketBinding binding;
    private BottomSheet datePickerBottomSheet;
    private long selectedId;
    private HotelRoom currentRoom = null;
    private CreateOrderRequest newPaymentPayload;
    private TourDate selectedDate;

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
            updateUi(date);
            newPaymentPayload.setAdults(0);
            newPaymentPayload.setChildren(0);
            newPaymentPayload.setAmount(0);
            binding.txtAdultQuantity.setText(String.valueOf(newPaymentPayload.getAdults()));
            binding.txtChildQuantity.setText(String.valueOf(newPaymentPayload.getChildren()));
            binding.txtTotalPrice.setText(String.valueOf(newPaymentPayload.getStringTotalPrice()));


            selectedDate = date;
            Log.d("DATEE", "init: " + date.toString());
        });

        HotelAdapter hotelAdapter = new HotelAdapter(this);
        hotelAdapter.setItemClickListener(new OnRecyclerViewHasNestedItemClickListener<Hotel, HotelRoom>() {
            @Override
            public void onClick(Hotel item, long position) {
                if(Objects.equals(item.getId(), newPaymentPayload.getHotelId())) {
                    newPaymentPayload.setHotelId(null);
                }
                else{
                    newPaymentPayload.setHotelId(item.getId());
                }
            }

            @Override
            public void onBlur(Hotel item, HotelRoom nestedItem) {
//                newPaymentPayload.setHotelId(null);
                newPaymentPayload.setRoomType("");
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() - (
                        nestedItem.getPrice() *
                        (newPaymentPayload.getAdults() +
                        (int) newPaymentPayload.getChildren()/2)));
                currentRoom = null;
                updateTotalPrice();
            }
        } );
        hotelAdapter.setNestedItemClickListener(new OnRecyclerViewItemClickListener<>() {
            @Override
            public void onClick(HotelRoom item, long position) {
                newPaymentPayload.setRoomType(item.getType());
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() + (
                        item.getPrice() *
                        (newPaymentPayload.getAdults() +
                        (int) newPaymentPayload.getChildren()/2))
                );
                currentRoom = item;
                updateTotalPrice();
            }

            @Override
            public void onBlur(HotelRoom item) {
                newPaymentPayload.setAmount(newPaymentPayload.getAmount() - (
                        item.getPrice() *
                        (newPaymentPayload.getAdults() +
                        (int) newPaymentPayload.getChildren()/2)));
                newPaymentPayload.setRoomType("");
                currentRoom = null;
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
                selectedDate = findDateById(res.getTourDates(), selectedId);

                newPaymentPayload.setTourDateId(selectedDate.getId());
                newPaymentPayload.setAdultPrice(selectedDate.getAdultPrice());
                newPaymentPayload.setChildPrice(selectedDate.getChildPrice());

                updateUi(selectedDate);

                Picasso.get()
                        .load(res.getTourImage())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgThumbnail);


                binding.layoutOpenCalendar.setOnClickListener(view -> {
                    showDatePickerBottomSheet();
                });

                binding.btnBack.setOnClickListener(view -> finish());

                binding.btnIncreaseAdult.setOnClickListener(view -> {
                    newPaymentPayload.increaseAdults(currentRoom);
                    binding.txtAdultQuantity.setText(String.valueOf(newPaymentPayload.getAdults()));
                    updateTotalPrice();
                });
                binding.btnDecreaseAdult.setOnClickListener(view -> {
                    newPaymentPayload.decreaseAdults(currentRoom);
                    binding.txtAdultQuantity.setText(String.valueOf(newPaymentPayload.getAdults()));
                    updateTotalPrice();
                });

                binding.btnIncreaseChild.setOnClickListener(view -> {
                    newPaymentPayload.increaseChilds(currentRoom);
                    binding.txtChildQuantity.setText(String.valueOf(newPaymentPayload.getChildren()));
                    updateTotalPrice();
                });

                binding.btnDecreaseChild.setOnClickListener(view -> {
                    newPaymentPayload.decreaseChilds(currentRoom);
                    binding.txtChildQuantity.setText(String.valueOf(newPaymentPayload.getChildren()));
                    updateTotalPrice();
                });

                binding.btnCreateOrder.setOnClickListener(view -> {
                    Log.d("roomId", "type: " + newPaymentPayload.getRoomType() + "; hotel: " + newPaymentPayload.getHotelId());
                    if(newPaymentPayload.getAdults() == 0 && newPaymentPayload.getChildren() == 0) {
                        Toast.makeText(this, "Vui lòng chọn số lượng vé!", Toast.LENGTH_SHORT).show();
                    } else if (newPaymentPayload.getHotelId() != null && newPaymentPayload.getRoomType() != null && newPaymentPayload.getRoomType().isEmpty()) {
                        Toast.makeText(this, "Vui lòng chọn loại phòng!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent confirmOrderIntent = new Intent(this, ConfirmOrderActivity.class);
                        confirmOrderIntent.putExtra("tour", new Gson().toJson(tour));
                        confirmOrderIntent.putExtra("tour-date", new Gson().toJson(selectedDate));
                        confirmOrderIntent.putExtra("payload", new Gson().toJson(newPaymentPayload));
                        startActivity(confirmOrderIntent);
                    }
                });
            });
        }
    }

    private void updateUi(TourDate selectedDate){
        binding.txtAdultPrice.setText(selectedDate.getStringAdultPrice());
        binding.txtChildPrice.setText(selectedDate.getStringChildPrice());
        newPaymentPayload.setTourDateId(selectedDate.getId());
        newPaymentPayload.setAdultPrice(selectedDate.getAdultPrice());
        newPaymentPayload.setChildPrice(selectedDate.getChildPrice());
        binding.txtSelectedDate.setText(selectedDate.getDepartDate());
        binding.txtAdultTicketRank.setText("Hạng vé: " + selectedDate.getType());
        binding.txtChildTicketRank.setText("Hạng vé: " + selectedDate.getType());
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