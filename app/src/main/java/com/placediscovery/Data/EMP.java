package com.placediscovery.Data;

/**
 * Created by ZuberSk on 04-Mar-16.
 */
public class EMP {
    private String EventName = "";
    private int sartDay;
    private int startMonth;
    private int startYear;
    private String venue = "";
    private String description = "";
    private int startHour, startMin;


    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getSartDay() {
        return sartDay;
    }

    public void setSartDay(int sartDay) {
        this.sartDay = sartDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }


}
