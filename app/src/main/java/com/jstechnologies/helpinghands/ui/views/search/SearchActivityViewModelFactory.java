package com.jstechnologies.helpinghands.ui.views.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.views.dashBoard.DashBoardViewModel;

public class SearchActivityViewModelFactory  implements ViewModelProvider.Factory  {

    ServiceRepository repository;

    public SearchActivityViewModelFactory(ServiceRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchActivityViewModel.class)) {
            return (T) new SearchActivityViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
