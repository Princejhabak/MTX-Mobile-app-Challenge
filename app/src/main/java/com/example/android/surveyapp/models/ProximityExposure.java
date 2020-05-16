package com.example.android.surveyapp.models;

import java.io.Serializable;

public class ProximityExposure implements Serializable {

    private String mobileNumber;
    private String address;
    private int distance;
    private String dateTime;

    public ProximityExposure() {
    }

    public ProximityExposure(String mobileNumber, String address, int distance, String dateTime) {
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.distance = distance;
        this.dateTime = dateTime;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
