package com.jstechnologies.helpinghandscloud;

import com.google.gson.Gson;

public class TaskResult {

    boolean success;
    String message;
    Object result;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }
    public <T>T getResult(Class<T> className) {
        return new Gson().fromJson((String)result,className);
    }
    public static TaskResult parseFrom(String response)
    {
        return new Gson().fromJson(response, TaskResult.class);
    }
}
