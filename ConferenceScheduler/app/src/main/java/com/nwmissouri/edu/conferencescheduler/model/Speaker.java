package com.nwmissouri.edu.conferencescheduler.model;

import java.io.Serializable;

public class Speaker implements Serializable {
    private String key;
    private String name;
    private String imageUrl;
    private String details;

    public Speaker() {
        // Empty constructor
    }

    public Speaker(String key, String name, String imageUrl, String details) {
        this.key = key;
        this.name = name;
        this.imageUrl = imageUrl;
        this.details = details;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
