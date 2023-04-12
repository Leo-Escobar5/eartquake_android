package com.leo_escobar.earthquake_monitor.api;

public class Geometry {
    private double[] coordinates;

    public double getLongitude(){
        return coordinates[0];
    }

    public double getLatitude(){
        return coordinates[1];
    }
}
