package com.jstechnologies.helpinghands.data.api.service;

import android.content.Context;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.ui.MyApp;
import com.jstechnologies.helpinghands.utils.ApiCallback;
import com.jstechnologies.helpinghands.utils.Functions;
import com.jstechnologies.helpinghands.utils.URLs;
import com.jstechnologies.helpinghandscloud.CloudRequest;
import com.jstechnologies.helpinghandscloud.OnCompleteListener;
import com.jstechnologies.helpinghandscloud.TaskResult;
import com.jstechnologies.helpinghandscloud.Tasks.CloudRequestTask;

import java.util.Arrays;


//This class fetch Services from Network
public class RemoteServiceApi {

    private static RemoteServiceApi mInstance;
    Context context;

    public static synchronized RemoteServiceApi getInstance(){
        if(mInstance==null)
            mInstance=new RemoteServiceApi(MyApp.getInstance().getApplicationContext());
        return mInstance;
    }

    public RemoteServiceApi(Context context) {
        this.context = context;
    }

    public void fetchDataFromRemote(Address address,RemoteServiceApiCallback remoteServiceApiCallback){
        if(remoteServiceApiCallback==null)
            return;

        if(!Functions.isNetworkConnected(context))
            remoteServiceApiCallback.onNetworkNotAvailable();

        genReadTask(address).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onTaskComplete(TaskResult result) {
                try{
                    if(result.isSuccess())
                        remoteServiceApiCallback.onSuccess(Arrays.asList(result.getResult(ServiceModel[].class)));
                    else
                        remoteServiceApiCallback.onError(1002,result.getMessage());


                }catch (Exception e)
                {
                    remoteServiceApiCallback.onError(1001,e.getMessage());
                }

            }
        }).execute();


    }

    public void saveServiceRemote(ServiceModel service,ApiCallback<ServiceModel> mApiCallback){
        if(mApiCallback==null)
            return;

        genWriteTask(service).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onTaskComplete(TaskResult result) {
                try{
                    if(result.isSuccess())
                        mApiCallback.onSuccess(null);
                    else
                        mApiCallback.onError(1002,result.getMessage());


                }catch (Exception e)
                {
                    mApiCallback.onError(1001,e.getMessage());
                }
            }
        }).execute();
    }

    private CloudRequestTask genReadTask(Address address) {

        String url= URLs.getUrlFor("services", CloudRequest.RequestType.READ);
        CloudRequest request= new CloudRequest();
        request.setRequestType(CloudRequest.RequestType.READ);
        request.setCollectionName("services_data");
        request.setPayload(address);
        return new CloudRequestTask<>(request,url);
    }

    private CloudRequestTask genWriteTask(ServiceModel serviceModel){

        String url= URLs.getUrlFor("services", CloudRequest.RequestType.CREATE);
        CloudRequest request= new CloudRequest();
        request.setRequestType(CloudRequest.RequestType.CREATE);
        request.setCollectionName("services_data");
        request.setPayload(serviceModel);
        return new CloudRequestTask<>(request,url);
    }

    public interface RemoteServiceApiCallback extends ApiCallback<ServiceModel>
    {
        void onNetworkNotAvailable();
    }
}
