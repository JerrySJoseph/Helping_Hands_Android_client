package com.jstechnologies.helpinghands.data.repository.service;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.jstechnologies.helpinghands.data.api.service.LocalServiceApi;
import com.jstechnologies.helpinghands.data.api.service.RemoteServiceApi;
import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.utils.ApiCallback;

import java.util.List;

public class ServiceRepository {

    private static ServiceRepository mInstance;
    private MutableLiveData<String>repositoryMessage;

    public static synchronized ServiceRepository getInstance(){
        if(mInstance==null)
            mInstance=new ServiceRepository();
        return mInstance;
    }

    public ServiceRepository() {
        repositoryMessage=new MutableLiveData<>();
    }

    public MutableLiveData<String> getRepositoryMessage() {
        return repositoryMessage;
    }

    public void getServices(Address address,ServiceCallback callback)
    {
        //First check if Remote Data-source is available
        RemoteServiceApi.getInstance().fetchDataFromRemote(address,new RemoteServiceApi.RemoteServiceApiCallback() {
            @Override
            public void onNetworkNotAvailable() {
                //TODO: Check Local Database for Cached Data

            }

            @Override
            public void onSuccess(List<ServiceModel> models) {
                callback.onSuccess(models);
                LocalServiceApi.getInstance().updateCache(models);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                repositoryMessage.setValue(errorMessage);
                //Fetch cached Data from Local DB
                LocalServiceApi.getInstance().fetchDataFromLocal(new LocalServiceApi.LocalServiceApiCallback() {
                    @Override
                    public void onSuccess(List<ServiceModel> models) {
                    //    repositoryMessage.setValue("Data fetched from local cache");
                        callback.onSuccess(models);
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        repositoryMessage.setValue(errorMessage);
                        callback.onFailure(errorMessage);
                    }
                });
            }
        });

    }

    public void saveService(ServiceModel service,ApiCallback<ServiceModel> mApiCallback)
    {
        RemoteServiceApi.getInstance().saveServiceRemote(service,mApiCallback);
    }


    public interface ServiceCallback{
        void onSuccess(List<ServiceModel> models);
        void onFailure(String reason);
    }
}
