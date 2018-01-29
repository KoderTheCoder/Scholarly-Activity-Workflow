package com.example.koder.activityworkflow;

/**
 * Created by ShivM on 24-Jan-18.
 */

public class Activity {

    String activityName;
    String UID;
    String hours;
    String date;
    Boolean approval;
    String location;
    String price;

    public Activity(){

    }

    public Activity(String activityName, String UID, String hours, String date, Boolean approval, String location, String price) {
        this.activityName = activityName;
        this.UID = UID;
        this.hours = hours;
        this.date = date;
        this.approval = approval;
        this.location = location;
        this.price = price;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getApproval() {
        return approval;
    }

    public void setApproval(Boolean approval) {
        this.approval = approval;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}


