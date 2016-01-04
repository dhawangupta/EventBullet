package com.placediscovery.MongoLabUser;

import java.io.Serializable;

/**
 * Created by Dhawan Gupta on 04-10-2015.
 */
public class User implements Serializable{

    public String user_id;
    public String name;
    public String email;
    public String password;
    public String savedplaces="";

    public User(){}

    public User(UserStatus userStatus){
        user_id = userStatus.getUser_Id();
        name = userStatus.getName();
        email = userStatus.getEmail();
        password = userStatus.getPassword();
        savedplaces = userStatus.getSavedPlaces();
    }
    public String getSavedplaces() {
        return savedplaces;
    }

    public void setSavedplaces(String savedplaces) {
        this.savedplaces = savedplaces;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
