package com.jstechnologies.helpinghands.data.repository.likes;

import android.content.Context;

import com.jstechnologies.helpinghands.data.api.likes.LocalLikesApi;
import com.jstechnologies.helpinghands.data.api.likes.RemoteLikesApi;
import com.jstechnologies.helpinghands.ui.MyApp;
import com.jstechnologies.helpinghands.utils.ApiCallback;

public class LikesRepository {

    private static LikesRepository mInstance;
    Context mContext;

    public static synchronized LikesRepository getInstance()
    {
        if(mInstance==null)
            mInstance=new LikesRepository();
        return mInstance;
    }

    private LikesRepository()
    {
        this.mContext= MyApp.getInstance().getApplicationContext();
    }

    public boolean hasLiked(String id)
    {
        return LocalLikesApi.getInstance(mContext).hasLiked(id);
    }

    public void saveLiked(String id,LikeCallback callback)
    {
        LocalLikesApi.getInstance(mContext).saveLiked(id);
        RemoteLikesApi.saveLikeToCloud(id,callback);
    }
    public void saveDisliked(String id,LikeCallback callback)
    {
        LocalLikesApi.getInstance(mContext).saveDisliked(id);
        RemoteLikesApi.saveDislikeToCloud(id,callback);
    }

    public interface LikeCallback{
        void onSuccess();
        void onError(String reason);
    }
}
