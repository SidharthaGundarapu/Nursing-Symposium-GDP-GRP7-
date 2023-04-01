package nwmsu.android.nursingsymposium.interactors;

import nwmsu.android.nursingsymposium.data.model.RegistrationModel;

public class RegistrationModelInteractor {

    public static boolean createUser(String username, String password, String confirmPassword) {
        return true;
    }

    public static boolean updateUser(int id, String username, String password, String confirmPassword) {
        return true;
    }

    public static boolean deleteUser(int id) {
        return true;
    }

    public static RegistrationModel getUserById(int id) {
        return new RegistrationModel();
    }
}
