package com.placediscovery.MongoLabUser;

import com.mongodb.BasicDBObject;

/**
 * Created by MOHIT on 01-12-2015.
 */

//TODO: using sharedpref to maintain global state of logged in user

public class UserStatus {
    public static boolean LoginStatus= false;
    public static String User_Id=null;
    public static String Name=null;
    public static String Email=null;
    public static String Password=null;
    public static String SavedPlaces=null;
    public static BasicDBObject[] Ratings=null;

    public static boolean isLoginStatus() {
        return LoginStatus;
    }

    public static void setLoginStatus(boolean loginStatus) {
        LoginStatus = loginStatus;
    }

    public static String getUser_Id() {
        return User_Id;
    }

    public static void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getSavedPlaces() {
        return SavedPlaces;
    }

    public static void setSavedPlaces(String savedPlaces) {
        SavedPlaces = savedPlaces;
    }

    public static BasicDBObject[] getRatings() {
        return Ratings;
    }

    public static void setRatings(BasicDBObject[] ratings) {
        Ratings = ratings;
    }

    public static void setUserStatus(User user) {
        LoginStatus=true;
        User_Id = user.getUser_id();
        Name = user.getName();
        Email = user.getEmail();
        Password = user.getPassword();
        SavedPlaces = user.getSavedplaces();
        Ratings = user.getRatings();
    }
}
