package com.example.nursingsymposium;

public class ConferenceModel {

    String name;
    String event;
    String time;

    public ConferenceModel(String name, String event, String time) {
        this.name = name;
        this.event = event;
        this.time = time;
    }

    public ConferenceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

 
}
