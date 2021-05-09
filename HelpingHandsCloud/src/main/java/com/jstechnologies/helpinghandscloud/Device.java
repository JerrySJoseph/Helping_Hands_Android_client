package com.jstechnologies.helpinghandscloud;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.GsonBuilder;


public class Device {

    String SHA_fingerprint;
    String packageName;
    String clientID;
    String fcmToken;


    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Device(){
    }

    public Device(String SHA_fingerprint, String clientID,String packageName) {
        this.SHA_fingerprint = SHA_fingerprint;
        this.clientID = clientID;
        this.packageName= packageName;
    }

    public String getSHA_fingerprint() {
        return SHA_fingerprint;
    }


    public String getClientID() {
        return clientID;
    }

    public static Device CreateClientForThisDevice(Context context, String SHA_fingerprint)
    {
        String uniqueDeviceID= Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return new Device(SHA_fingerprint,uniqueDeviceID,context.getPackageName());
    }


    public String toJSON()
    {
        return new GsonBuilder().disableHtmlEscaping().create().toJson(this).replaceAll("\\\\n", "");
    }

}
