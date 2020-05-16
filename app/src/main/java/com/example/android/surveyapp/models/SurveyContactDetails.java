package com.example.android.surveyapp.models;

import java.io.Serializable;

/** Model class to hold the details if user agrees to share contact */
public class SurveyContactDetails implements Serializable {

    private String relationship;
    private String name;
    private String detailGender;
    private String detailAge;
    private String addressLine1;
    private String addressLine2;
    private String phoneNo;

    public SurveyContactDetails() {
    }

    public SurveyContactDetails(String relationship, String name, String detailGender, String detailAge, String addressLine1, String addressLine2, String phoneNo) {
        this.relationship = relationship;
        this.name = name;
        this.detailGender = detailGender;
        this.detailAge = detailAge;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.phoneNo = phoneNo;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailGender() {
        return detailGender;
    }

    public void setDetailGender(String detailGender) {
        this.detailGender = detailGender;
    }

    public String getDetailAge() {
        return detailAge;
    }

    public void setDetailAge(String detailAge) {
        this.detailAge = detailAge;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
