package com.jstechnologies.helpinghands.ui.views.dashBoard;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.MyApp;
import com.jstechnologies.helpinghands.utils.AddressUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardViewModel extends ViewModel {

    ServiceRepository repository;

    private final MutableLiveData<List<ServiceModel>> servicesListLiveData;
    private final MutableLiveData<String>messageLiveData;
    private final MutableLiveData<Boolean>isLoadingLiveData;
    private final MutableLiveData<Boolean>noDataLiveData;
    private final MutableLiveData<Address>addressLiveData;

    public DashBoardViewModel(ServiceRepository repository) {
        this.repository = repository;
        servicesListLiveData= new MutableLiveData<>();
        messageLiveData=new MutableLiveData<>();
        isLoadingLiveData=new MutableLiveData<>();
        noDataLiveData=new MutableLiveData<>();
        addressLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<Address> getAddressLiveData() {
        return addressLiveData;
    }

    public MutableLiveData<List<ServiceModel>> getServicesListLiveData() {
        return servicesListLiveData;
    }

    public LiveData<String> getRepositoryMessage() {
        return repository.getRepositoryMessage();
    }

    public MutableLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public MutableLiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public void fetchAddress(Activity activity)
    {

        try {
            addressLiveData.setValue(MyApp.getInstance().getAddress(activity));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void fetchData()
    {
        isLoadingLiveData.setValue(true);
        repository.getServices(addressLiveData.getValue(),mfetchDataCallback);
    }

    private ServiceRepository.ServiceCallback mfetchDataCallback= new ServiceRepository.ServiceCallback() {
        @Override
        public void onSuccess(List<ServiceModel> models) {
            isLoadingLiveData.setValue(false);
            servicesListLiveData.setValue(models);
        }

        @Override
        public void onFailure(String reason) {
            isLoadingLiveData.setValue(false);
            messageLiveData.setValue(reason);
            noDataLiveData.setValue(true);
        }
    };
}
