package com.placediscovery.MongoLabPlace;

import java.io.Serializable;

/**
 * Created by Dhawan Gupta on 03-02-2016.
 */
public class Event implements Serializable {


    private String name = "";
    //User(or Event Organiser) Status to be implemented in database....I think.
    private String status = "";
    //organisers website
    private String eventUrl = "";
    private String profilePicUrl = "";
    private String timings = "";
    private String type = "";
    private String ticket = "";
    private String freq = "";
    private String duration = "";
    private String feedimageURL = "";
    private String content = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String eduration) {
        this.duration = eduration;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getFeedimageURL() {
        return feedimageURL;
    }

    public void setFeedimageURL(String imageURL) {
        this.feedimageURL = imageURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
