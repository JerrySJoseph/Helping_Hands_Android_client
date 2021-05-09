package com.jstechnologies.helpinghands.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class Functions {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
