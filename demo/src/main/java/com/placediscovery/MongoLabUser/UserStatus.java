package com.placediscovery.MongoLabUser;

/**
 * Created by MOHIT on 01-12-2015.
 */
public class UserStatus {
    public static boolean LoginStatus= false;
    public static String User_Id=null;
    public static String Name=null;
    public static String Email=null;
    public static String Password=null;
    // this is for status
    public void SetStatus(boolean status){
        this.LoginStatus = status;
    }
    public boolean GetStatus(){
        return this.LoginStatus;
    }

    // this is for userid
    public void SetUser_Id(String user_Id){
        this.User_Id = user_Id;
    }
    public String GetUser_Id(){
        return this.User_Id;
    }

    //this is for name
    public void SetName(String name){
        this.Name = name;
    }
    public String GetName(){
        return this.Name;
    }

    //this is for email
    public void SetEmail(String email){
        this.Email = email;
    }
    public String GetEmail(){
        return this.Email;
    }

    //this is for password
    public void SetPassword(String password){
        this.Password = password;
    }
    public String GetPassword(){
        return this.Password;
    }
}
