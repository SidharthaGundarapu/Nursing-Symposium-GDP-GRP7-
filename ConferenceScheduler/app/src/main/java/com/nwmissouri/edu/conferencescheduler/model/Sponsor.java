package com.nwmissouri.edu.conferencescheduler.model;

import java.io.Serializable;

public class Sponsor implements Serializable {
    
    private String name;
    private String type;
    private String imageUrl;
    private String key;
    private String details;
    
    public Sponsor() {
        // Empty constructor
    }
    
    public Sponsor(String name, String type, String imageUrl, String key, String details) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.key = key;
        this.details = details;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
}
