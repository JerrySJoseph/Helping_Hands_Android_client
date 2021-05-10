package com.jstechnologies.helpinghands.ui.views.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;

import java.util.List;

public class SearchActivityViewModel extends ViewModel {

    ServiceRepository repository;

    private final MutableLiveData<List<ServiceModel>> servicesListLiveData;
    private final MutableLiveData<String>messageLiveData;
    private final MutableLiveData<Boolean>isLoadingLiveData;
    private final MutableLiveData<Boolean>noDataLiveData;


    public SearchActivityViewModel(ServiceRepository repository) {
        this.repository = repository;
        servicesListLiveData= new MutableLiveData<>();
        messageLiveData=new MutableLiveData<>();
        isLoadingLiveData=new MutableLiveData<>();
        noDataLiveData=new MutableLiveData<>();
    }

    public LiveData<List<ServiceModel>> getServicesListLiveData() {
        return servicesListLiveData;
    }

    public LiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public LiveData<Boolean> getNoDataLiveData() {
        return noDataLiveData;
    }

}
