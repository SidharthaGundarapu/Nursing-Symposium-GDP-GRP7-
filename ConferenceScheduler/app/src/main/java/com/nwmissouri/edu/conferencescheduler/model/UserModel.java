
package com.nwmissouri.edu.conferencescheduler.model;

public class UserModel {
    private String email;
    private String password;
    private String userType;
    private String name;

    UserModel() {}

    public UserModel(String email, String password, String userType, String name) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
