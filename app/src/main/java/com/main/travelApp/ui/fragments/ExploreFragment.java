package com.main.travelApp.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.main.travelApp.adapters.TourListExploreAdapter;
import com.main.travelApp.databinding.FragmentExploreBinding;
import com.main.travelApp.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private TourListExploreAdapter tourAdapter;
    private List<Tour> tours;
    private FragmentExploreBinding exploreBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exploreBinding = FragmentExploreBinding.inflate(inflater, container, false);

        return exploreBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view){
        tourAdapter = new TourListExploreAdapter();
        exploreBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        fetchTours();

        tourAdapter.setTours(tours);
        exploreBinding.rcvExploreTours.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreBinding.rcvExploreTours.setAdapter(tourAdapter);
    }

    private void fetchTours(){
        tours = new ArrayList<>();
        tours.add(new Tour(1L, "Tour Hạ Long 11111", 4.5f, "Hanoi", "2023-11-15", 3, 2500000L, 23)); // 2,500,000 VNĐ
        tours.add(new Tour(2L, "Tour Đà Nẵng", 4.2f, "Ho Chi Minh City", "2023-11-20", 5, 3500000L, 123)); // 3,500,000 VNĐ
        tours.add(new Tour(3L, "Tour Nha Trang", 4.8f, "Hanoi", "2023-11-25", 4, 4000000L, 8172)); // 4,000,000 VNĐ
        tours.add(new Tour(4L, "Tour Phú Quốc", 4.7f, "Da Nang", "2023-11-30", 7, 6000000L, 1723)); // 6,000,000 VNĐ
        tours.add(new Tour(5L, "Tour Sapa", 4.3f, "Hanoi", "2023-12-05", 2, 1500000L, 12873)); // 1,500,000 VNĐ
        tours.add(new Tour(6L, "Tour Đà Lạt", 4.6f, "Da Nang", "2023-12-10", 3, 1800000L, 1273)); // 1,800,000 VNĐ
        tours.add(new Tour(7L, "Tour Cần Thơ", 4.9f, "Ho Chi Minh City", "2023-12-15", 2, 1200000L, 1923)); // 1,200,000 VNĐ
        tours.add(new Tour(8L, "Tour Huế", 4.4f, "Da Nang", "2023-12-20", 4, 3000000L, 52423)); // 3,000,000 VNĐ
        tours.add(new Tour(9L, "Tour Hội An", 4.1f, "Da Nang", "2023-12-25", 3, 2200000L, 2934)); // 2,200,000 VNĐ
        tours.add(new Tour(10L, "Tour Mũi Né", 4.0f, "Ho Chi Minh City", "2023-12-30", 4, 2800000L, 28734)); // 2,800,000 VNĐ
    }
}