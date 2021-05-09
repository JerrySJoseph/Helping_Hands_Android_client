package com.jstechnologies.helpinghands.data.db.services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jstechnologies.helpinghands.data.db.Converters;
import com.jstechnologies.helpinghands.data.model.ServiceModel;
import com.jstechnologies.helpinghands.utils.ApiCallback;
import com.jstechnologies.helpinghands.utils.DatabaseExecutor;

import java.util.List;

@Database(entities = ServiceModel.class ,exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class ServiceDB extends RoomDatabase {

    private static String DB_NAME="helping_hands_cache_db";
    private static ServiceDB mInstance;

    public static synchronized ServiceDB getInstance(Context context){
        if(mInstance==null)
        {
            mInstance= Room.databaseBuilder(context.getApplicationContext(),ServiceDB.class,DB_NAME).fallbackToDestructiveMigration().build();
        }
        return mInstance;
    }
    protected abstract ServiceDao serviceDao();

    public void getAll(ApiCallback<ServiceModel> mCallBack)
    {
        new DatabaseExecutor().execute(() -> {
            try{
                List<ServiceModel> models=serviceDao().getAll();
                post(()->mCallBack.onSuccess(models));

            }catch (Exception e)
            {
                post(()->mCallBack.onError(1003,e.getMessage()));
            }
        });
    }
    public void saveAll(List<ServiceModel> models)
    {
        new DatabaseExecutor().execute(() -> {
            try{
                serviceDao().insertServices(models);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    public void updateCache(List<ServiceModel> models)
    {
        new DatabaseExecutor().execute(() -> {
            try{
                serviceDao().deleteAll();
                serviceDao().insertServices(models);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
    void post(Runnable runnable)
    {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
