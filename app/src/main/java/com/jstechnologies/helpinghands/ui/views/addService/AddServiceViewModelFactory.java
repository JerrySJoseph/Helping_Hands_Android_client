package com.jstechnologies.helpinghands.ui.views.addService;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModel;

public class AddServiceViewModelFactory implements ViewModelProvider.Factory {

   ServiceRepository serviceRepository;

    public AddServiceViewModelFactory(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddServiceViewModel.class)) {
            return (T) new AddServiceViewModel(serviceRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
