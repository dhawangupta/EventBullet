package com.placediscovery.Data;

/**
 * Created by ZuberSk on 04-Mar-16.
 */
public class EMP {
    private String OrganiserFirstName = "";
    private String OrganiserSecondName = "";
    private String EventName = "";
    private int day, month, year;
    private String PhoneNo = "";
    private String venue = "";

    public String getOrganiserFirstName() {
        return OrganiserFirstName;
    }

    public void setOrganiserFirstName(String organiserFirstName) {
        OrganiserFirstName = organiserFirstName;
    }

    public String getOrganiserSecondName() {
        return OrganiserSecondName;
    }

    public void setOrganiserSecondName(String organiserSecondName) {
        OrganiserSecondName = organiserSecondName;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
