package com.placediscovery.MongoLabPlace;

import java.io.Serializable;

/**
 * Created by Dhawan Gupta on 03-02-2016.
 */
public class Event implements Serializable {
    private String name = "";
    private String timings = "";
    private String type = "";
    private String ticket = "";
    private String freq = "";
    private String duration = "";
    private String imageURL = "";
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
