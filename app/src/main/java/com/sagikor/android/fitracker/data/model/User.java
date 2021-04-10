package com.sagikor.android.fitracker.data.model;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String fullName;
    private String email;
    private String userID;
    private List<String> classes = new ArrayList<>();

    public User(String fullName, String email, String userId) {
        this.fullName = fullName;
        this.email = email;
        this.userID = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public List<String> getClasses() {
        return classes;
    }


    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }


}
