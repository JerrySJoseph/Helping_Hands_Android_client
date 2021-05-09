package com.jstechnologies.helpinghands.data.db.services;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jstechnologies.helpinghands.data.model.ServiceModel;

import java.util.List;


@Dao
public interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertServices(List<ServiceModel> serviceModels);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertService(ServiceModel serviceModel);

    @Delete
    void delete(ServiceModel serviceModel);

    @Delete
    void delete(ServiceModel... serviceModels);

    @Query("SELECT * FROM services_cache")
    List<ServiceModel> getAll();

    @Query("DELETE FROM services_cache")
    void deleteAll();
}
