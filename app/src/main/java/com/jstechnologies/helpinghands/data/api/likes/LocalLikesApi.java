package com.jstechnologies.helpinghands.data.api.likes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class LocalLikesApi {

     SharedPreferences preferences;
     SharedPreferences.Editor editor;

    private static LocalLikesApi mInstance;

    public static synchronized LocalLikesApi getInstance(Context context)
    {
        if(mInstance==null)
            mInstance=new LocalLikesApi(context);
        return mInstance;
    }

    private LocalLikesApi(Context context)
    {
        preferences=context.getSharedPreferences("local_likes_prefrences",Context.MODE_PRIVATE);
        editor=preferences.edit();
    }
    public boolean hasLiked(String id)
    {
        Set<String>ids=preferences.getStringSet("likes_id_set",new HashSet<>());
        Set<String>dids=preferences.getStringSet("dislikes_id_set",new HashSet<>());
        return ids.contains(id) || dids.contains(id);
    }

    public void saveLiked(String id)
    {
        Set<String>ids=preferences.getStringSet("likes_id_set",new HashSet<>());
        ids.add(id);
        editor.putStringSet("likes_id_set",ids);
        editor.commit();
    }

    public void saveDisliked(String id)
    {
        Set<String>ids=preferences.getStringSet("dislikes_id_set",new HashSet<>());
        ids.add(id);
        editor.putStringSet("dislikes_id_set",ids);
        editor.commit();
    }
}
