package com.example.android.surveyapp.models;


/** Model class to store the details of covid positive patient */
public class CovidPositive {

    private String mobileNumber;
    private double latitude;
    private double longitude;

    public CovidPositive() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
}
