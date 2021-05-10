package com.jstechnologies.helpinghandscloud;

public class CloudRequest {

    public enum RequestType{
        READ,
        CREATE,
        LIKE,
        DISLIKE,
        REPORT,
        COMMENT
    }
    RequestType requestType;
    Object payload;
    String collectionName;

    public RequestType getRequestType() {
        return requestType;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
