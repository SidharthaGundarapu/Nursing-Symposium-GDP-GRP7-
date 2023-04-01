package nwmsu.android.nursingsymposium.interactors;

import android.util.Pair;

import nwmsu.android.nursingsymposium.ConferenceDataModel;

import java.util.ArrayList;
import java.util.Arrays;

public class ConferenceModelInteractor {

    protected ArrayList<String> validAttributes = new ArrayList<>(
            Arrays.asList("image", "name", "location", "date",
                    "eventName", "time", "speaker", "studentKey",
                    "speakerkey", "eventDescription", "aboutSpeaker"));

    public static boolean createConference() {
        return true;
    }

    public boolean updateConference(int id, ArrayList<Pair<String, String>> attributes) {
        for (Pair<String, String> attr : attributes) {
            if (this.validAttributes.contains(attr)) {
                String reference = attr.getKey();
                this.reference = attr.getValue();
            }
        }
        return true;
    }

    public static boolean deleteConference(int id) {
        return true;
    }

    public static ConferenceDataModel getConferenceById(int id) {
        return new ConferenceDataModel();
    }
}
