package nwmsu.android.nursingsymposium;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConferenceDataModelTest {

    @Test
    public void creates_model() {
        String test_image = "location/ref";
        String test_name = "conferenceName";
        String test_location = "conferenceLocation";
        String test_date = "11/11/1111";
        String test_eventName = "eventName";
        String test_time = "10:00";
        String test_speaker = "speakerName";
        String test_studentKey = "12";
        String test_speakerKey = "13";
        String test_eventDescription = "Event of things";
        String test_aboutSpeaker = "The speaker for the event";

        ConferenceDataModel myModel = new ConferenceDataModel(
                test_image, test_name, test_location, test_date,
                test_eventName, test_time, test_speaker, test_studentKey,
                test_speakerKey, test_eventDescription, test_aboutSpeaker);

        assertEquals(myModel.getImage(), test_image);
        assertEquals(myModel.getName(), test_name);
        assertEquals(myModel.getLocation(), test_location);
        assertEquals(myModel.getDate(), test_date);
        assertEquals(myModel.getEventName(), test_eventName);
        assertEquals(myModel.getTime(), test_time);
        assertEquals(myModel.getSpeaker(), test_speaker);
        assertEquals(myModel.getStudentKey(), test_studentKey);
        assertEquals(myModel.getSpeakerkey(), test_speakerKey);
        assertEquals(myModel.getEventDescription(), test_eventDescription);
        assertEquals(myModel.getAboutSpeaker(), test_aboutSpeaker);
    }
}
