package com.leo_escobar.earthquake_monitor.main;

import androidx.lifecycle.LiveData;

import com.leo_escobar.earthquake_monitor.Earthquake;
import com.leo_escobar.earthquake_monitor.api.EarthquakeJSONResponse;
import com.leo_escobar.earthquake_monitor.api.EqApiClient;
import com.leo_escobar.earthquake_monitor.api.Features;
import com.leo_escobar.earthquake_monitor.database.EqDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {


    private final EqDatabase database;

    public interface DownloadStatusListener {
        void downloadSuccess();
        void downloadError(String message);
    }

    public MainRepository(EqDatabase database) {
        this.database = database;
    }
    public LiveData<List<Earthquake>> getEqList() {
        return database.eqDao().getEarthquakes();
    }
    public void downloadAndSaveEarthwuakes(DownloadStatusListener downloadStatusListener) {
        EqApiClient.EqService service = EqApiClient.getInstance().getService();

        service.getEarthquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {



                List<Earthquake> earthquakeList= getEarthquakesWithMoshi(response.body());


                EqDatabase.databaseWriteExecutor.execute(() -> {
                    database.eqDao().insertAll(earthquakeList);
                });
                downloadStatusListener.downloadSuccess();
            }

            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {
                downloadStatusListener.downloadError("There was an error in downloading earthquakes, check your internet connection.");
            }
        });
        //this.eqList.setValue(eqList);
    }

    private List<Earthquake> getEarthquakesWithMoshi(EarthquakeJSONResponse body) {
        ArrayList<Earthquake> eqList = new ArrayList<>();
        List<Features> features = body.getFeatures();
        for (Features feature : features) {
            String id = feature.getId();
            double magnitude = feature.getProperties().getMag();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();
            double longitude = feature.getGeometry().getLongitude();
            double latitude = feature.getGeometry().getLatitude();

            Earthquake earthquake = new Earthquake(id, place, magnitude, time, longitude, latitude);
            eqList.add(earthquake);
        }
        return eqList;
    }
}
