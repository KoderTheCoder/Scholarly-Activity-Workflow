package com.example.koder.activityworkflow;

import java.io.Serializable;

/**
 * Created by ShivM on 24-Jan-18.
 */

public class Activity implements Serializable{

    private String activityName;
    private String UID;
    private String hours;
    private String date;
    private Boolean approval;
    private String location;
    private String price;
    private String username;
    private String email;
    private String category;

    public Activity(){

    }

    public Activity(String activityName, String UID, String hours, String date, Boolean approval, String location, String price, String category) {
        this.setActivityName(activityName);
        this.setUID(UID);
        this.setHours(hours);
        this.setDate(date);
        this.setApproval(approval);
        this.setLocation(location);
        this.setPrice(price);
        this.setCategory(category);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


