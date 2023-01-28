package nwmsu.android.conferenceapp.dbmodels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Conference {

    protected String name;
    protected String description;
    protected Date start_date;
    protected Date end_date;
    protected boolean publicly_viewable_flag;
    protected Date created_date;
    protected Date updated_date;

    public Conference() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Conference(String name, String description, Date start_date, Date end_date) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.publicly_viewable_flag = false;
    }

    public Conference(String name, String description, Date start_date, Date end_date, boolean viewable) {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.publicly_viewable_flag = viewable;
    }

    public Map<String, Object> getDocument() {
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("name", this.name);
        myModel.put("description", this.description);
        myModel.put("start_date", this.start_date);
        myModel.put( "end_date", this.end_date);
        myModel.put("publicly_viewable_flag", this.publicly_viewable_flag);
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
}