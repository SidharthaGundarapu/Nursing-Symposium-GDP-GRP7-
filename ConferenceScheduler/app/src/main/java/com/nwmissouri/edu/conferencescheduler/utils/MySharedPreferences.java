package com.nwmissouri.edu.conferencescheduler.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.nwmissouri.edu.conferencescheduler.model.UserModel;

public class MySharedPreferences {
    private static final String SHARED_PREFERENCES_NAME = "my_prefs";
    private static MySharedPreferences instance;
    private final SharedPreferences sharedPreferences;

    private MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static MySharedPreferences initialize(Context context) {
        if (instance == null) {
            instance = new MySharedPreferences(context);
        }
        return instance;
    }

    public static MySharedPreferences getInstance() {
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void putLong(String key, Long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public Long getLong(String key, Long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public Boolean isUserLoggedIn() {
        return getString(Constants.USER_ID) != null;
    }

    public String userId() {
        return getString(Constants.USER_ID);
    }

    public String userType() {
        return getString(Constants.USER_TYPE);
    }

    public UserModel getUser() {
        String name = getString(Constants.USER_NAME);
        String email = getString(Constants.USER_EMAIL);
        String userType = getString(Constants.USER_TYPE);
        return new UserModel(email, "", userType, name);
    }

    public void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveSuperAdminUser() {
        saveUser(Constants.ID_SUPER_ADMIN, Constants.NAME_SUPER_ADMIN, Constants.EMAIL_SUPER_ADMIN, Constants.USER_TYPE_SUPER_ADMIN);
    }

    public void saveUser(String id, String name, String email, String userType) {
        putString(Constants.USER_ID, id);
        putString(Constants.USER_NAME, name);
        putString(Constants.USER_EMAIL, email);
        putString(Constants.USER_TYPE, userType);
    }

    public void updateName(String updatedName) {
        putString(Constants.USER_NAME, updatedName);
    }
}
