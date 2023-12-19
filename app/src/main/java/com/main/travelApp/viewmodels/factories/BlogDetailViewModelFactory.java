package com.main.travelApp.viewmodels.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.main.travelApp.viewmodels.BlogDetailViewModel;

public class BlogDetailViewModelFactory implements ViewModelProvider.Factory {
    private final long id;

    public BlogDetailViewModelFactory(long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BlogDetailViewModel.class)) {
            return (T) new BlogDetailViewModel(id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

