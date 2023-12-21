package com.main.travelApp.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.main.travelApp.viewmodels.BlogDetailViewModel;

public class BlogDetailViewModelFactory implements ViewModelProvider.Factory {
    private final long id;
    private String accessToken;

    public BlogDetailViewModelFactory(String accessToken, long id) {
        this.id = id;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BlogDetailViewModel.class)) {
            return (T) new BlogDetailViewModel(accessToken, id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

