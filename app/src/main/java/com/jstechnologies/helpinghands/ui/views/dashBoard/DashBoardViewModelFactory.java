package com.jstechnologies.helpinghands.ui.views.dashBoard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;

public class DashBoardViewModelFactory implements ViewModelProvider.Factory {

    ServiceRepository repository;

    public DashBoardViewModelFactory(ServiceRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DashBoardViewModel.class)) {
            return (T) new DashBoardViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
