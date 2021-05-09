package com.jstechnologies.helpinghands.data.repository.likes;

import android.content.Context;

import com.jstechnologies.helpinghands.data.api.likes.LocalLikesApi;
import com.jstechnologies.helpinghands.ui.MyApp;

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

    public void saveLiked(String id)
    {
        LocalLikesApi.getInstance(mContext).saveLiked(id);
    }
}
