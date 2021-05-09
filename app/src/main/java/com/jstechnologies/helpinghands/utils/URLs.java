package com.jstechnologies.helpinghands.utils;

import com.jstechnologies.helpinghandscloud.CloudRequest;

public class URLs {
    private static String ROOT_URL="http://192.168.1.26:3000/api/v1";
    public static String getUrlFor(String route, CloudRequest.RequestType requestType)
    {
        return ROOT_URL+"/"+route+"/"+requestType.name().toLowerCase();
    }
}
