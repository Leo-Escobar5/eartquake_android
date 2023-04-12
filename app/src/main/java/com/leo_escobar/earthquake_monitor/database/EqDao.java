package com.leo_escobar.earthquake_monitor.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.leo_escobar.earthquake_monitor.Earthquake;

import java.util.List;

@Dao
public interface EqDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);

    @Query("SELECT * FROM earthquakes ORDER BY magnitude DESC")
    LiveData<List<Earthquake>> getEarthquakes();



}
