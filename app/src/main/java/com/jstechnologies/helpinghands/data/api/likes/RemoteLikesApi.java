package com.jstechnologies.helpinghands.data.api.likes;

import android.util.Log;

import com.jstechnologies.helpinghands.data.model.Address;
import com.jstechnologies.helpinghands.data.repository.likes.LikesRepository;
import com.jstechnologies.helpinghands.utils.URLs;
import com.jstechnologies.helpinghandscloud.CloudRequest;
import com.jstechnologies.helpinghandscloud.OnCompleteListener;
import com.jstechnologies.helpinghandscloud.TaskResult;
import com.jstechnologies.helpinghandscloud.Tasks.CloudRequestTask;

public class RemoteLikesApi {

    public static void saveLikeToCloud(String id, LikesRepository.LikeCallback callback)
    {
        genLikeRequest(id, CloudRequest.RequestType.LIKE).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onTaskComplete(TaskResult result) {
                if(result.isSuccess())
                    callback.onSuccess();
                else
                    callback.onError(result.getMessage());
            }
        }).execute();
    }
    public static void saveDislikeToCloud(String id, LikesRepository.LikeCallback callback)
    {
        genLikeRequest(id, CloudRequest.RequestType.DISLIKE).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onTaskComplete(TaskResult result) {
                if(result.isSuccess())
                    callback.onSuccess();
                else
                    callback.onError(result.getMessage());
            }
        }).execute();
    }

    private static CloudRequestTask genLikeRequest(String id, CloudRequest.RequestType requestType){

        String url= URLs.getUrlFor("services", requestType);
        CloudRequest request= new CloudRequest();
        request.setRequestType(requestType);
        request.setCollectionName("services_data");
        request.setPayload(id);
        return new CloudRequestTask<>(request,url);
    }
}
