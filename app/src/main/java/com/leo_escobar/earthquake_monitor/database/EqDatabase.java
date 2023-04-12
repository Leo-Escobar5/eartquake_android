package com.leo_escobar.earthquake_monitor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.leo_escobar.earthquake_monitor.Earthquake;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Earthquake.class}, version = 1)
public abstract class EqDatabase extends RoomDatabase {

    public abstract EqDao eqDao();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile EqDatabase INSTANCE;
    public static EqDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (EqDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EqDatabase.class, "earthquakes_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
