package com.example.koder.activityworkflow;

/**
 * Created by Koder on 1/30/2018.
 */

public class User {

    public String name;
    public String email;
    public String userID;
    public int level;

    public User(){

    }

    public User(String name, String email, String userId, int level){
        this.name = name;
        this.email = email;
        this.userID = userId;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
