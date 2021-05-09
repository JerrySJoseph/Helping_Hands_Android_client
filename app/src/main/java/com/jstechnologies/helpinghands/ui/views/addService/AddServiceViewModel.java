package com.jstechnologies.helpinghands.ui.views.addService;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.service.ServiceRepository;
import com.jstechnologies.helpinghands.ui.MyApp;
import com.jstechnologies.helpinghands.utils.AddressUtils;
import com.jstechnologies.helpinghands.utils.ApiCallback;

import java.io.IOException;
import java.util.List;

public class AddServiceViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isSavingLiveData;
    private final MutableLiveData<String> messageLiveData;
    private final MutableLiveData<Boolean> isSaveSuccess;
    private final MutableLiveData<Address> addressLiveData;

    ServiceRepository repository;

    public AddServiceViewModel(ServiceRepository repository) {
        this.repository = repository;
        isSavingLiveData=new MutableLiveData<>();
        messageLiveData=new MutableLiveData<>();
        isSaveSuccess= new MutableLiveData<>();
        addressLiveData=new MutableLiveData<>();
    }

    public MutableLiveData<Address> getAddressLiveData() {
        return addressLiveData;
    }

    public MutableLiveData<Boolean> getIsSavingLiveData() {
        return isSavingLiveData;
    }

    public MutableLiveData<String> getMessageLiveData() {
        return messageLiveData;
    }

    public MutableLiveData<Boolean> getIsSaveSuccess() {
        return isSaveSuccess;
    }

    public void saveService(ServiceModel serviceModel)
    {
        isSavingLiveData.setValue(true);
        serviceModel.setAddress(addressLiveData.getValue());
        repository.saveService(serviceModel, new ApiCallback<ServiceModel>() {
            @Override
            public void onSuccess(List<ServiceModel> models) {
                isSavingLiveData.setValue(false);
                messageLiveData.setValue("Service saved Successfully");
                isSaveSuccess.setValue(true);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                isSavingLiveData.setValue(false);
                messageLiveData.setValue(errorMessage);
                isSaveSuccess.setValue(false);
            }
        });

    }

    public void fetchCurrentAddress(Activity activity)
    {
        try {
            addressLiveData.setValue(MyApp.getInstance().getAddress(activity));
        } catch (IOException e) {
            messageLiveData.setValue("Error getting location: "+e.getMessage());
        }
    }
}
