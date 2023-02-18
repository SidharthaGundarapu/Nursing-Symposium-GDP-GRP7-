package nwmsu.android.nursingsymposium;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RegistrationModelTest {
    @Test
    public void creates_model() {
        String test_id = "12";
        String test_username = "testerman";
        String test_password = "password";
        String test_userType = "user";

        RegistrationModel myModel = new RegistrationModel(test_id, test_username, test_password, test_userType);

        assertEquals(myModel.getId(), test_id);
        assertEquals(myModel.getUsername(), test_username);
        assertEquals(myModel.getPassword(), test_password);
        assertEquals(myModel.getUserType(), test_userType);
    }
}
