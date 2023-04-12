package com.leo_escobar.earthquake_monitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leo_escobar.earthquake_monitor.Earthquake;
import com.leo_escobar.earthquake_monitor.api.RequestStatus;
import com.leo_escobar.earthquake_monitor.api.StatusWithDescription;
import com.leo_escobar.earthquake_monitor.database.EqDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MainRepository repository;
    private MutableLiveData<StatusWithDescription> statusMutableLiveData = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application) {
        super(application);
        EqDatabase database = EqDatabase.getDatabase(application);
        this.repository = new MainRepository(database);
    }

    public LiveData<List<Earthquake>> getEqList() {
        return repository.getEqList();
    }

    public LiveData<StatusWithDescription> getStatusMutableLiveData() {
        return statusMutableLiveData;
    }

    public void downloadEarthquakes() {
        statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.LOADING, ""));

        repository.downloadAndSaveEarthwuakes(new MainRepository.DownloadStatusListener() {
            @Override
            public void downloadSuccess() {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.DONE, ""));
            }

            @Override
            public void downloadError(String message) {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.ERROR, message));
            }
        });

    }


   /* private List<Earthquake> parseEarthquakes(String responseString) {
        ArrayList<Earthquake> eqList = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(responseString);
            JSONArray featuresJsonArray = jsonResponse.getJSONArray("features");

            for ( int i = 0; i< featuresJsonArray.length(); i++){
                JSONObject jsonFeature = featuresJsonArray.getJSONObject(i);
                String id = jsonFeature.getString("id");
                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");
                double magnitude = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                long time = jsonProperties.getLong("time");
                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coordinatesJSONArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJSONArray.getDouble(0);
                double latitude = coordinatesJSONArray.getDouble(1);

                Earthquake earthquake = new Earthquake(id, place, magnitude, time, longitude, latitude);
                eqList.add(earthquake);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eqList;
    }
*/
}
