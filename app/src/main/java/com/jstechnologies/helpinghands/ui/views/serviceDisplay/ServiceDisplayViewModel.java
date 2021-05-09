package com.jstechnologies.helpinghands.ui.views.serviceDisplay;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.data.repository.likes.LikesRepository;

public class ServiceDisplayViewModel extends ViewModel {


    private final MutableLiveData<ServiceModel> servicesLiveData;
    private final MutableLiveData<String>messageLiveData;
    private final MutableLiveData<Boolean>isLoadingLiveData;
    private final MutableLiveData<Boolean>noDataLiveData;
    private final MutableLiveData<Boolean>hasLiked;

    public ServiceDisplayViewModel(ServiceModel model) {

        servicesLiveData= new MutableLiveData<>();
        messageLiveData=new MutableLiveData<>();
        isLoadingLiveData=new MutableLiveData<>();
        noDataLiveData=new MutableLiveData<>();
        hasLiked= new MutableLiveData<>();
        this.servicesLiveData.setValue(model);
        this.hasLiked.setValue(LikesRepository.getInstance().hasLiked(model.get_id()));
    }

    public LiveData<ServiceModel> getServicesLiveData() {
        return servicesLiveData;
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

    public LiveData<Boolean> getHasLiked() {
        return hasLiked;
    }

    public void toggleLike()
    {
        LikesRepository.getInstance().saveLiked(servicesLiveData.getValue().get_id());
    }
    public void toggleDislike()
    {

    }
}
