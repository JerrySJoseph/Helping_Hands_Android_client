package com.jstechnologies.helpinghands.data.api.service;

import android.content.Context;

import com.jstechnologies.helpinghands.data.db.services.ServiceDB;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.ui.MyApp;
import com.jstechnologies.helpinghands.utils.ApiCallback;

import java.util.List;

public class LocalServiceApi {

    private static LocalServiceApi mInstance;
    Context mContext;

    public static synchronized LocalServiceApi getInstance(){
        if(mInstance==null)
            mInstance=new LocalServiceApi(MyApp.getInstance().getApplicationContext());
        return mInstance;
    }
    LocalServiceApi(Context context)
    {
        this.mContext=context;
    }

    public void fetchDataFromLocal(LocalServiceApiCallback callback)
    {
        ServiceDB.getInstance(mContext).getAll(callback);
    }
   public void saveAll(List<ServiceModel>models)
   {
       ServiceDB.getInstance(mContext).saveAll(models);
   }
    public void updateCache(List<ServiceModel>models)
    {
        ServiceDB.getInstance(mContext).updateCache(models);
    }


    public interface LocalServiceApiCallback extends ApiCallback<ServiceModel>{

    }
}
