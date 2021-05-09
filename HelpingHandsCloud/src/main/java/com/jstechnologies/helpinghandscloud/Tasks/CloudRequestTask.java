package com.jstechnologies.helpinghandscloud.Tasks;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jstechnologies.helpinghandscloud.CloudRequest;
import com.jstechnologies.helpinghandscloud.HelpingHandsCloudApp;
import com.jstechnologies.helpinghandscloud.Task;
import com.jstechnologies.helpinghandscloud.TaskResult;
import com.jstechnologies.helpinghandscloud.VolleySingletonQueue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CloudRequestTask<T> extends Task<T> {

    CloudRequest object;
    String Url;

    public CloudRequestTask(CloudRequest object, String url) {
        this.object = object;
        Url = url;
    }

    @Override
    public void run() {

        StringRequest request= new StringRequest(Request.Method.POST,Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                TaskResult result=new TaskResult();
                try{
                    result=TaskResult.parseFrom(response);
                    if(result==null)
                        throw new Exception("Error parsing response from server");
                }catch (Exception e)
                {
                    result=new TaskResult();
                    result.setSuccess(false);
                    result.setMessage(e.getMessage());
                }finally {
                    fireOnComplete(result);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                TaskResult result=new TaskResult();
                result.setSuccess(false);
                try{
                    if(error instanceof TimeoutError)
                    {
                        result.setMessage("Request Timed out. Server unavailable or busy");
                    }
                    else
                    {
                        String reponse=new String(error.networkResponse.data);
                        result=TaskResult.parseFrom(reponse);
                    }


                }catch (Exception e)
                {
                    result.setMessage(e.getMessage());
                }
                finally {
                    fireOnComplete(result);
                }

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("device_fingerprint",HelpingHandsCloudApp.getInstance().getThisDevice().toJSON());
                return headers;
            }

            @Override
            public byte[] getBody() {
                return new Gson().toJson(object).getBytes();
            }
        };
        VolleySingletonQueue.getInstance(HelpingHandsCloudApp.getInstance().getmContext()).addToRequestQueue(request);
    }

}
