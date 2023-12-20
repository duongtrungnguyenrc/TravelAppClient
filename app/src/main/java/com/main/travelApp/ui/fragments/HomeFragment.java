package com.main.travelApp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.travelApp.R;
import com.main.travelApp.adapters.PlaceListAdapter;
import com.main.travelApp.adapters.NewestPostsAdapter;
import com.main.travelApp.adapters.TourListAdapter;
import com.main.travelApp.databinding.FragmentHomeBinding;
import com.main.travelApp.ui.activities.MainActivity;
import com.main.travelApp.ui.components.ExpiredDialog;
import com.main.travelApp.utils.LayoutManagerUtil;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.HomeViewModel;
import com.main.travelApp.viewmodels.factories.HomeViewModelFactory;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private TourListAdapter tourListAdapter;
    private PlaceListAdapter placeListAdapter;
    private NewestPostsAdapter newestPostsAdapter;
    private FragmentHomeBinding homeBinding;
    private HomeViewModel homeViewModel;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        sharedPreferences = requireActivity().getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, Context.MODE_PRIVATE);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModelFactory factory = new HomeViewModelFactory(sharedPreferences);
        homeViewModel = new ViewModelProvider(getActivity(), factory).get(HomeViewModel.class);

        init();
        setEvents();
    }

    private void init(){
        tourListAdapter = new TourListAdapter(getActivity());
        placeListAdapter = new PlaceListAdapter();
        newestPostsAdapter = new NewestPostsAdapter();
        newestPostsAdapter.setContext(getContext());

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

        homeViewModel.getIsExpired().observe(getViewLifecycleOwner(), isExpired -> {
            if(isExpired){
                ExpiredDialog dialog = new ExpiredDialog(getContext(), sharedPreferences);
                dialog.show(getParentFragmentManager(), "EXPIRED_DIALOG");
            }
        });

        homeViewModel.verifyUser(sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, ""));

        homeBinding.rcvPlaces.setLayoutManager(LayoutManagerUtil.disabledScrollGridManager(getContext(), 2));
        homeBinding.pgPosts.setAdapter(newestPostsAdapter);
        homeBinding.txtUserName.setText(getString(R.string.user_welcome) + " " + homeViewModel.getCurrentUser().getFullName());
        if(!homeViewModel.getCurrentUser().getAvatar().isEmpty() && homeViewModel.getCurrentUser().getAvatar() != null)
            Picasso.get()
                    .load(homeViewModel.getCurrentUser().getAvatar())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(homeBinding.userAvatar);
    }

    private void setEvents(){
        homeBinding.btnMoreTour.setOnClickListener(this);
        homeBinding.btnMoreTour1.setOnClickListener(this);
        homeBinding.btnMoreBlog.setOnClickListener(this);
        homeBinding.userAvatar.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view == homeBinding.btnMoreTour || view == homeBinding.btnMoreTour1){
            ((MainActivity) requireActivity()).changeFragment(2);
        }else if(view == homeBinding.btnMoreBlog){
            ((MainActivity) requireActivity()).changeFragment(3);
        }else if(view == homeBinding.userAvatar){
            ((MainActivity) requireActivity()).changeFragment(4);
        }
    }
}