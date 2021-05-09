package com.jstechnologies.helpinghandslocaldb;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface Dao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(T... objects);

    @Delete
    void delete(T user);

    @Query("SELECT * FROM datamodel ")
    List<T> getAll();
}
