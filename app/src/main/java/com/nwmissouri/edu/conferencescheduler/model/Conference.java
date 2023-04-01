package com.nwmissouri.edu.conferencescheduler.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Conference implements Serializable {
    private String key;
    private String name;
    private String imageUrl;

    @ServerTimestamp
    private Date date;

    public Conference() {
        // Empty constructor required for Firebase
    }

    public Conference(String key, String name, String imageUrl) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
