package com.jstechnologies.helpinghands.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.utils.AddressUtils;
import com.jstechnologies.helpinghandscloud.HelpingHandsCloudApp;

import java.io.IOException;

public class MyApp extends Application {

    private static MyApp mInstance;
    private Address address;

    public static synchronized MyApp getInstance(){
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        HelpingHandsCloudApp.init(this,"http://192.168.1.26:3000/api/v1");
    }

    public Address getAddress(Activity activity) throws IOException {
        if(address==null)
            address=new AddressUtils().getAddressFromGps(activity);
        return address;
    }
}
