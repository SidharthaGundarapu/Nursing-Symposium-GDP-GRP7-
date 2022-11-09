package nwmsu.android.conferenceapp.tables;

import java.util.ArrayList;

public class EventTableModel {

    public static class EventInstance {
        // Main table data
        public int event_id;
        public String name;
        public String description;
        public String startTime;
        public String endTime;
        public ArrayList<Integer> speakers;

        // App Tracking
        public boolean trackEvent;

        // Audit Related Info
        public String created_date;
        public String updated_date;
        public String updated_userId;

        public int count;
        public double timeMinutes;

        public EventInstance(int tID, String tName, String tDescription, String tStart, String tEnd) {
            this.event_id = tID;
            this.name = tName;
            this.description = tDescription;
            this.startTime = tStart;
            this.endTime = tEnd;
            this.speakers = new ArrayList<>();
            this.trackEvent = false;
        }

        public EventInstance(int tID, String tName, String tDescription, String tStart, String tEnd, ArrayList<Integer> tSpeakers) {
            this.event_id = tID;
            this.name = tName;
            this.description = tDescription;
            this.startTime = tStart;
            this.endTime = tEnd;
            this.speakers = tSpeakers;
            this.trackEvent = false;
        }

        public boolean addSpeaker(int speakerID) {
            try {
                this.speakers.add( speakerID);
                return true;
            } catch (Exception e) {
                // Can't add for some reason
                e.printStackTrace();
                return false;
            }
        }
    }

    // Instantiate a model for our singleton
    public static EventTableModel theModel = null;

    public static EventTableModel getSingleton() {
        if ( theModel == null) {
            theModel = new EventTableModel();
        }
        return theModel;
    }

    public ArrayList<EventInstance> eventsList;

    private EventTableModel() {
        newEventInstance();
    }

    public void newEventInstance() {
        this.eventsList = new ArrayList<EventInstance>();
        // Replace with database checking later
        loadStaticInfo();
    }

    private void loadStaticInfo() {
        loadEventInstance( 1, "Monitoring Heartrates",
                "Join our speakers in learning about how modern techniques have advanced in monitoring heartrates.",
                "1:00 PM", "2:00 PM");
        loadEventInstance( 2, "Electric Therapy",
                "How do we approach cardiovascular diseases using electric therapy? Find out here!",
                "2:00 PM", "3:00 PM");
        loadEventInstance( 3, "Symptoms of Heart Disease",
                "Discuss common symptoms of heart disease and how to diagnose early.",
                "1:30 PM", "2:30 PM");
        loadEventInstance( 4, "Blood Pressure and You",
                "Blood pressure go up is bad, go down is good. Yes",
                "4:00 PM", "5:00 PM");
        loadEventInstance( 5, "Cardiovascular diseases",
                "Learn about rare and common cardiovascular diseases.",
                "3:30 PM", "4:30 PM");
    }

    private void loadEventInstance(int id, String name, String description, String start, String end) {
        eventsList.add(new EventInstance(id, name, description, start, end));
    }

    public void trackEvent(int id) {
        if (this.eventsList != null) {
            // does event exist in list?
                // if so, set its property to tracked
        }
    }

    public void untrackEvent(int id) {
        if (this.eventsList != null) {
            // does event exist in list?
                // if so, set its property to be untracked
        }
    }
}
