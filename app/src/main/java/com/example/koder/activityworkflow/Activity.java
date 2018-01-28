package com.example.koder.activityworkflow;

/**
 * Created by ShivM on 24-Jan-18.
 */

public class Activity {

    Integer ID;
    String activityName;
    String hours;
    String date;
    Boolean approval;
    String location;

    public Activity(Integer ID, String activityName, String hours, String date, Boolean approval, String location) {
        this.ID = ID;
        this.activityName = activityName;
        this.hours = hours;
        this.date = date;
        this.approval = approval;
        this.location = location;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
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
}

