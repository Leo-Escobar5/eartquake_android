package com.leo_escobar.earthquake_monitor;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;
@Entity(tableName = "earthquakes")
public class Earthquake {

    @PrimaryKey
    @NonNull
    private final String id;
    private final String place;
    private final double magnitude;
    private final long time;
    private final double latitude;
    private final double longitude;

    //constructor
    public Earthquake(String id, String place, double magnitude, long time, double latitude, double longitude) {
        this.id = id;
        this.place = place;
        this.magnitude = magnitude;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    //getters
    public String getId() {
        return id;
    }

    public String getPlace() {
        return place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public long getTime() {
        return time;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Earthquake that = (Earthquake) o;
        return Double.compare(that.magnitude, magnitude) == 0 && time == that.time && Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && id.equals(that.id) && place.equals(that.place);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, place, magnitude, time, latitude, longitude);
    }
}
