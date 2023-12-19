package com.main.travelApp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.travelApp.adapters.PlaceListAdapter;
import com.main.travelApp.adapters.NewestPostsAdapter;
import com.main.travelApp.adapters.TourListAdapter;
import com.main.travelApp.databinding.FragmentHomeBinding;
import com.main.travelApp.ui.activities.MainActivity;
import com.main.travelApp.utils.LayoutManagerUtil;
import com.main.travelApp.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {
    private TourListAdapter tourListAdapter;
    private PlaceListAdapter placeListAdapter;
    private NewestPostsAdapter newestPostsAdapter;
    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        init();
        setEvents();
    }

    private void init(){
        tourListAdapter = new TourListAdapter(getActivity());
        placeListAdapter = new PlaceListAdapter();
        newestPostsAdapter = new NewestPostsAdapter();

        homeViewModel.getTours().observe(getViewLifecycleOwner(), tours -> {
            tourListAdapter.setTours(tours.getTours());
        });

        homeViewModel.getPlaces().observe(getViewLifecycleOwner(), places -> {
            placeListAdapter.setPlaces(places.subList(0, 4));
        });

        homeBinding.rcvTours.setAdapter(tourListAdapter);
        homeBinding.rcvTours.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));

        homeBinding.rcvPlaces.setAdapter(placeListAdapter);

        homeViewModel.getNewestPosts().observe(getViewLifecycleOwner(), posts -> {
            newestPostsAdapter.setPosts(posts);
        });

        homeBinding.rcvPlaces.setLayoutManager(LayoutManagerUtil.disabledScrollGridManager(getContext(), 2));

        homeBinding.pgPosts.setAdapter(newestPostsAdapter);
    }

    private void setEvents(){
        homeBinding.btnMoreTour.setOnClickListener(onClickListener());
        homeBinding.btnMoreTour1.setOnClickListener(onClickListener());
        homeBinding.btnMoreBlog.setOnClickListener(onClickListener());
    }

    private View.OnClickListener onClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == homeBinding.btnMoreTour || view == homeBinding.btnMoreTour1){
                    ((MainActivity) requireActivity()).changeFragment(2);
                }else if(view == homeBinding.btnMoreBlog){
                    ((MainActivity) requireActivity()).changeFragment(3);
                }
            }
        };
    }
}