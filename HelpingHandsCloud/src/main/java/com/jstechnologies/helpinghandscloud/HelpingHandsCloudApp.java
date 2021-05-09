package com.jstechnologies.helpinghandscloud;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelpingHandsCloudApp {

    private static HelpingHandsCloudApp mInstance;
    private static Context mContext;
    private static String TAG="HelpingHands-cloud-App";
    private static String SHA_fingerprint=null;
    private static String _SERVER_URL=null;

    //Client factory
    static private DeviceFactory mDeviceFactory = new DeviceFactory() {
        @Override
        public Device newClient() {
            return Device.CreateClientForThisDevice(mContext,SHA_fingerprint);
        }
    };

    public static synchronized HelpingHandsCloudApp getInstance()
    {
        if(mContext==null)
            throw new IllegalStateException("Cloud App not initialized");
        if(mInstance==null)
            mInstance=new HelpingHandsCloudApp();
        return mInstance;
    }

    public static void init(Context context,String SERVER_URL){
        mContext=context;
        _SERVER_URL=SERVER_URL;
        getAppFingerPrint();
    }


    public Context getmContext() {
        return mContext;
    }

    public Device getThisDevice() {
        return mDeviceFactory.newClient();
    }
    private static void getAppFingerPrint()
    {

        Log.e("KeyHash",mContext.getPackageName());
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(signature.toByteArray());
                SHA_fingerprint= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("KeyHash", SHA_fingerprint);
            }
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash Error",e.getMessage());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash Error",e.getMessage());
        }
    }
}
