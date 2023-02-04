package nwmsu.android.conferenceapp.dbmodels;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {

    protected int id;
    protected String username;
    protected String email;
    protected int user_role;
    protected Date created_date;
    protected Date updated_date;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.user_role = 0; // Guest
    }

    public User(String username, String email, int user_role) {
        this.username = username;
        this.email = email;
        this.user_role = user_role;
    }

    public Map<String, Object> getDocument() {
        Map<String, Object> myModel = new HashMap<>();
        myModel.put("username", this.username);
        myModel.put("email", this.email);
        return myModel;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public int getUser_role() {
        return this.user_role;
    }

    public Date getCreated_date() {
        return this.created_date;
    }

    public Date getUpdated_date() {
        return this.updated_date;
    }
}