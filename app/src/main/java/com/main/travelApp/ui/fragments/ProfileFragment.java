package com.main.travelApp.ui.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.travelApp.databinding.FragmentProfileBinding;
import com.main.travelApp.services.auth.AuthManager;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding profileBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false);

//        Log.d("user", AuthManager.getInstance().getCurrentUser().getFullName());
        return profileBinding.getRoot();
    }
}