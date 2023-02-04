package nwmsu.android.conferenceapp.dbmodels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Speaker {

    protected int id;
    protected String name;
    protected String bio;
    protected Date created_date;
    protected Date updated_date;

    public Speaker() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Speaker(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public Map<String, Object> getDocument() {
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("name", this.name);
        myModel.put("bio", this.bio);
        return myModel;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBio() {
        return this.bio;
    }

    public Date getCreated_date() {
        return this.created_date;
    }

    public Date getUpdated_date() {
        return this.updated_date;
    }
}