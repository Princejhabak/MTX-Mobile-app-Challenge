package com.example.android.surveyapp.models;

/** Model class to hold the details of notifications */
public class Notification {

    private String notificationDateTime;
    private String notificationHeading;
    private String notificationBody;
    private boolean notificationRead;


    public Notification() {
    }

    public Notification(String notificationDateTime, String notificationHeading, String notificationBody, boolean notificationRead) {
        this.notificationDateTime = notificationDateTime;
        this.notificationHeading = notificationHeading;
        this.notificationBody = notificationBody;
        this.notificationRead = notificationRead;
    }

    public String getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(String notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    public String getNotificationHeading() {
        return notificationHeading;
    }

    public void setNotificationHeading(String notificationHeading) {
        this.notificationHeading = notificationHeading;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public boolean isNotificationRead() {
        return notificationRead;
    }

    public void setNotificationRead(boolean notificationRead) {
        this.notificationRead = notificationRead;
    }
}
