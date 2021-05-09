package com.jstechnologies.helpinghandscloud;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingletonQueue {

    private static VolleySingletonQueue mInstance;
    RequestQueue requestQueue;
    Context mContext;

    public static synchronized VolleySingletonQueue getInstance(Context mContext){
        if(mInstance==null)
            mInstance=new VolleySingletonQueue(mContext);
        return mInstance;
    }

    private VolleySingletonQueue(Context mContext)
    {
        this.mContext=mContext;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
           requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
