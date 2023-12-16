package com.main.travelApp.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.main.travelApp.adapters.TourListExploreAdapter;
import com.main.travelApp.databinding.FragmentExploreBinding;
import com.main.travelApp.models.GeneralTour;
import com.main.travelApp.ui.activities.MainActivity;
import com.main.travelApp.viewmodels.TourViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    private TourListExploreAdapter tourAdapter;
    private FragmentExploreBinding exploreBinding;
    private TourViewModel tourViewModel;
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
        tourViewModel = new ViewModelProvider(requireActivity()).get(TourViewModel.class);
        init(view);
        setEvents();
    }

    private void init(View view){
        tourAdapter = new TourListExploreAdapter();

        exploreBinding.rcvExploreTours.setLayoutManager(new GridLayoutManager(getContext(), 2));
        exploreBinding.rcvExploreTours.setAdapter(tourAdapter);

        tourViewModel.getTours()
                .observe(getViewLifecycleOwner(), tours -> {
                    tourAdapter.setTours(tours.getTours());
                });

        exploreBinding.btnBack.setOnClickListener(onClickListener());

    }
    private void setEvents(){
        exploreBinding.btnBack.setOnClickListener(onClickListener());
        exploreBinding.btnNextPage.setOnClickListener(onClickListener());
        exploreBinding.btnPrevPage.setOnClickListener(onClickListener());
    }

    private View.OnClickListener onClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == exploreBinding.btnBack){
                    ((MainActivity)getActivity()).changeFragment(1);
                }else if(view == exploreBinding.btnNextPage){
                    if(tourViewModel.getPage() < tourViewModel.getTours().getValue().getPages()){
                        tourViewModel.setPage(tourViewModel.getPage() + 1);
                    }
                }else if (view == exploreBinding.btnPrevPage){
                    if(tourViewModel.getPage() > 1){
                        tourViewModel.setPage(tourViewModel.getPage() - 1);
                    }
                }
            }
        };
    }
}