package com.jstechnologies.helpinghands.ui.views.serviceDisplay;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModel;

public class ServiceDisplayViewModelFactory implements ViewModelProvider.Factory {

    ServiceModel model;
    int index=-1;
    public ServiceDisplayViewModelFactory(ServiceModel model) {
        this.model = model;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ServiceDisplayViewModel.class)) {
            return (T) new ServiceDisplayViewModel(model);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
