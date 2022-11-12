package nwmsu.android.conferenceapp;

public class EventRecyclerData {

    private String name;
    private String description;
    private String time;
    private int iconID;
    private boolean tracked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int icon_id) {
        this.iconID = icon_id;
    }

    public boolean getTracked() {
        return this.tracked;
    }

    public void toggleTracked() {
        this.tracked = !this.tracked;
    }

    public EventRecyclerData(int tIcon, String tName, String tDescription, String tTime) {
        this.iconID = tIcon;
        this.name = tName;
        this.description = tDescription;
        this.time = tTime;
        this.tracked = false;
    }

    public EventRecyclerData(int tIcon, String tName, String tDescription, String tTime, Boolean tTracked) {
        this.iconID = tIcon;
        this.name = tName;
        this.description = tDescription;
        this.time = tTime;
        this.tracked = tTracked;
    }
}
