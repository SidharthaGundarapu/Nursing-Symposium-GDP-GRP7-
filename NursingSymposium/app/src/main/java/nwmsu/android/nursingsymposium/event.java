package main.java.nwmsu.android.nursingsymposium;

import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private String conferenceId;
    private String key;
    private String type;

    private String eventName;
    private String eventDescription;
    private String speaker;
    private String aboutSpeaker;
    private String location;
    private String date;
    private String time;
    private String imageUrl;
    private String speakerKey;
    @ServerTimestamp
    private Date eventTime;
    private String subscribeDocumentID;

    private String surveyLink;

    public Event() {
    }

    public Event(String conferenceId, String key, String type, String eventName, String eventDescription, String speaker, String aboutSpeaker, String location, String date, String time, String imageUrl, String speakerKey, Date eventTime, String subscribeDocumentID, String surveyLink) {
        this.conferenceId = conferenceId;
        this.key = key;
        this.type = type;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.speaker = speaker;
        this.aboutSpeaker = aboutSpeaker;
        this.location = location;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
        this.speakerKey = speakerKey;
        this.eventTime = eventTime;
        this.subscribeDocumentID = subscribeDocumentID;
        this.surveyLink = surveyLink;
    }

    public String getConferenceId() {
        return conferenceId;
    }

    public String getKey() {
        return key;
    }
    public String getSurveyLink() {
        return surveyLink;
    }
    public String getType() {
        return type;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getAboutSpeaker() {
        return aboutSpeaker;
    }

    public String getLocation() {
        return location;
    }
