package com.example.unsan.easybuygps;

/**
 * Created by Unsan on 9/4/18.
 */

public class Tracking {
    String carNumber;
    String customer;
    public Tracking()
    {

    }

    public Tracking(String carNumber, double latitude, double longitude, double destLatitude, double destLongitude,String customer) {
        this.carNumber = carNumber;
        this.latitude = latitude;
        this.longitude = longitude;

        this.destLatitude = destLatitude;
        this.destLongitude = destLongitude;
        this.customer=customer;
    }

    double latitude;


    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public double getDestLatitude() {
        return destLatitude;
    }

    public void setDestLatitude(double destLatitude) {
        this.destLatitude = destLatitude;
    }

    public double getDestLongitude() {
        return destLongitude;
    }

    public void setDestLongitude(double destLongitude) {
        this.destLongitude = destLongitude;
    }

    double longitude;

    double destLatitude;
    double destLongitude;
}

