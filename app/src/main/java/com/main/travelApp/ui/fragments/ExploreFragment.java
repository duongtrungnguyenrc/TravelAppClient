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
import com.main.travelApp.models.GeneralTour;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private TourListExploreAdapter tourAdapter;
    private List<GeneralTour> generalTours;
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

        tourAdapter.setTours(generalTours);
        exploreBinding.rcvExploreTours.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreBinding.rcvExploreTours.setAdapter(tourAdapter);
    }

    private void fetchTours(){
        generalTours = new ArrayList<>();
    }
}