package nwmsu.android.nursingsymposium.interactors;

import nwmsu.android.nursingsymposium.EventModel;

public class EventModelInteractor {

    public static boolean createEvent(String eventName, String description, DateTime startTime, DateTime endTime) {
        return true;
    }

    public static boolean updateEvent(int id, String username, String password, String confirmPassword) {
        return true;
    }

    public static boolean deleteEvent(int id) {
        return true;
    }

    public static EventModel getEventById(int id) {
        return new EventModel();
    }
}
