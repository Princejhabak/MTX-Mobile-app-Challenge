package com.example.android.surveyapp.models;

import java.io.Serializable;
import java.util.List;

/** Model class to hold the details of surveys taken by the user */
public class Survey implements Serializable {

    private String dateTime;
    //private String language

    private  boolean enableNotification;
    private String age;
    private String country;
    private String gender;
    private String travelHistory;
    private String feeling;
    private boolean testedPositive;
    private List<String> symptoms;
    private boolean shareContacts;
    private List<SurveyContactDetails> surveyContactDetailsList;

    private String closeContact;
    private boolean medicalCare;
    private List<String> preConditionsList;
    private List<String> preventionStepsList;
    private List<String> concernsList;

    public Survey() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isEnableNotification() {
        return enableNotification;
    }

    public void setEnableNotification(boolean enableNotification) {
        this.enableNotification = enableNotification;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTravelHistory() {
        return travelHistory;
    }

    public void setTravelHistory(String travelHistory) {
        this.travelHistory = travelHistory;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public boolean isTestedPositive() {
        return testedPositive;
    }

    public void setTestedPositive(boolean testedPositive) {
        this.testedPositive = testedPositive;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }

    public boolean isShareContacts() {
        return shareContacts;
    }

    public void setShareContacts(boolean shareContacts) {
        this.shareContacts = shareContacts;
    }

    public List<SurveyContactDetails> getSurveyContactDetailsList() {
        return surveyContactDetailsList;
    }

    public void setSurveyContactDetailsList(List<SurveyContactDetails> surveyContactDetailsList) {
        this.surveyContactDetailsList = surveyContactDetailsList;
    }

    public String getCloseContact() {
        return closeContact;
    }

    public void setCloseContact(String closeContact) {
        this.closeContact = closeContact;
    }

    public boolean isMedicalCare() {
        return medicalCare;
    }

    public void setMedicalCare(boolean medicalCare) {
        this.medicalCare = medicalCare;
    }

    public List<String> getPreConditionsList() {
        return preConditionsList;
    }

    public void setPreConditionsList(List<String> preConditionsList) {
        this.preConditionsList = preConditionsList;
    }

    public List<String> getPreventionStepsList() {
        return preventionStepsList;
    }

    public void setPreventionStepsList(List<String> preventionStepsList) {
        this.preventionStepsList = preventionStepsList;
    }

    public List<String> getConcernsList() {
        return concernsList;
    }

    public void setConcernsList(List<String> concernsList) {
        this.concernsList = concernsList;
    }
}
