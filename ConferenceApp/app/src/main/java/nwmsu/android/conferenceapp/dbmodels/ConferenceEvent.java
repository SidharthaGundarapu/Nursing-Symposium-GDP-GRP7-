package nwmsu.android.conferenceapp.dbmodels;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConferenceEvent {

    protected int id;
    protected String name;
    protected String description;
    protected Date start_date;
    protected Date end_date;
    protected boolean publicly_viewable_flag;
    protected Date created_date;
    protected Date updated_date;

    protected String conference_id;
    protected String[] usersAttending;
    protected String[] keynoteSpeakers;
    protected String[] breakoutspeakers;

    public ConferenceEvent() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ConferenceEvent(String conference_id, String name, String description, Date start_date, Date end_date) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.publicly_viewable_flag = false;
        this.conference_id = conference_id;
    }

    public ConferenceEvent(String conference_id, String name, String description, Date start_date, Date end_date, boolean viewable) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.publicly_viewable_flag = viewable;
        this.conference_id = conference_id;
    }

    public Map<String, Object> getDocument() {
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("name", this.name);
        myModel.put("description", this.description);
        myModel.put("start_date", this.start_date);
        myModel.put( "end_date", this.end_date);
        myModel.put("publicly_viewable_flag", this.publicly_viewable_flag);
        myModel.put("conference_id", this.conference_id);
        myModel.put("users_attending", this.usersAttending);
        return myModel;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStart_date() {
        return this.start_date;
    }

    public Date getEnd_date() {
        return this.end_date;
    }

    public boolean isPublicly_viewable_flag() {
        return this.publicly_viewable_flag;
    }

    public Date getCreated_date() {
        return this.created_date;
    }

    public Date getUpdated_date() {
        return this.updated_date;
    }

    public String getConference_id() {
        return this.conference_id;
    }

    public String[] getUsersAttending() {
        return this.usersAttending;
    }

    public String[] getKeynoteSpeakers() { return this.keynoteSpeakers; }

    public String[] getBreakoutspeakers() { return this.breakoutspeakers; }

    public boolean isUserAttendingEvent( String username) {
        boolean contains = Arrays.asList( this.usersAttending).contains( username);
        return contains;
    }

}