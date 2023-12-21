package com.main.travelApp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.adapters.PlaceListAdapter;
import com.main.travelApp.adapters.NewestPostsAdapter;
import com.main.travelApp.adapters.TourListAdapter;
import com.main.travelApp.adapters.SearchResultAdapter;
import com.main.travelApp.callbacks.ActionCallback;
import com.main.travelApp.databinding.FragmentHomeBinding;
import com.main.travelApp.repositories.impls.SearchServiceImpl;
import com.main.travelApp.response.SearchResponse;
import com.main.travelApp.ui.activities.MainActivity;
import com.main.travelApp.ui.activities.SupportActivity;
import com.main.travelApp.ui.components.ExpiredDialog;
import com.main.travelApp.utils.DebounceUtil;
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
    private SearchServiceImpl searchService;
    private SearchResultAdapter tourSearchResultAdapter;
    private SearchResultAdapter postSearchResultAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    @SuppressLint("SetTextI18n")
    private void init(){
        tourListAdapter = new TourListAdapter(getActivity());
        placeListAdapter = new PlaceListAdapter();
        newestPostsAdapter = new NewestPostsAdapter();
        newestPostsAdapter.setContext(getContext());
        searchService = new SearchServiceImpl();
        tourSearchResultAdapter = new SearchResultAdapter(getActivity());
        postSearchResultAdapter = new SearchResultAdapter(getActivity());

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

        homeBinding.rcvToursResult.setAdapter(tourSearchResultAdapter);
        homeBinding.rcvToursResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeBinding.rcvPostsResult.setAdapter(postSearchResultAdapter);
        homeBinding.rcvPostsResult.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeBinding.txtUserName.setText(getString(R.string.user_welcome) + " " + homeViewModel.getCurrentUser().getFullName());
        if(!homeViewModel.getCurrentUser().getAvatar().isEmpty() && homeViewModel.getCurrentUser().getAvatar() != null)
            Picasso.get()
                    .load(homeViewModel.getCurrentUser().getAvatar())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(homeBinding.imgAvatar);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = requireActivity().getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    private void setEvents() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);

        homeBinding.btnMoreTour.setOnClickListener(this);
        homeBinding.btnMoreTour1.setOnClickListener(this);
        homeBinding.btnMoreBlog.setOnClickListener(this);
        homeBinding.imgAvatar.setOnClickListener(this);
        homeBinding.btnSupport.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SupportActivity.class);
            startActivity(intent);
        });

        homeBinding.edtSearch.setOnFocusChangeListener((view, isFocus) -> {
            if(isFocus) {
                homeBinding.layoutFloatingSearchResult.startAnimation(animation);
                homeBinding.layoutFloatingSearchResult.setVisibility(View.VISIBLE);
                homeBinding.btnSearchCancel.setVisibility(View.VISIBLE);
                homeBinding.btnSupport.setVisibility(View.GONE);
            }
        });

        homeBinding.edtSearch.addTextChangedListener(new DebounceUtil(text -> handleSearch(text.toString())));

        homeBinding.btnSearchCancel.setOnClickListener(view -> {
            homeBinding.layoutFloatingSearchResult.setVisibility(View.GONE);
            homeBinding.btnSearchCancel.setVisibility(View.GONE);
            homeBinding.btnSupport.setVisibility(View.VISIBLE);
            hideKeyboard();
            homeBinding.edtSearch.setText("");
            homeBinding.edtSearch.clearFocus();
            homeBinding.layoutRecentActivity.setVisibility(View.VISIBLE);
            homeBinding.layoutSearchResult.setVisibility(View.GONE);
        });

        homeBinding.btnClearResult.setOnClickListener(view -> {
            homeBinding.edtSearch.setText("");
            homeBinding.layoutRecentActivity.setVisibility(View.VISIBLE);
            homeBinding.layoutSearchResult.setVisibility(View.GONE);
        });

    }

    @Override
    public void onClick(View view) {
        if(view == homeBinding.btnMoreTour || view == homeBinding.btnMoreTour1){
            ((MainActivity) requireActivity()).changeFragment(2);
        }else if(view == homeBinding.btnMoreBlog){
            ((MainActivity) requireActivity()).changeFragment(3);
        }else if(view == homeBinding.imgAvatar){
            ((MainActivity) requireActivity()).changeFragment(4);
        }
    }

    private void handleSearch(String keyword) {
        if(!keyword.trim().isEmpty()) {
            searchService.search(keyword, new ActionCallback<>() {
                @Override
                public void onSuccess(SearchResponse response) {
                    tourSearchResultAdapter.setTours(response.getTours());
                    postSearchResultAdapter.setPosts(response.getPosts());
                    homeBinding.layoutRecentActivity.setVisibility(View.GONE);
                    homeBinding.layoutSearchResult.setVisibility(View.VISIBLE);
                    hideKeyboard();
                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            tourSearchResultAdapter.setTours(null);
            postSearchResultAdapter.setPosts(null);
            homeBinding.layoutRecentActivity.setVisibility(View.VISIBLE);
            homeBinding.layoutSearchResult.setVisibility(View.GONE);
        }
    }
}