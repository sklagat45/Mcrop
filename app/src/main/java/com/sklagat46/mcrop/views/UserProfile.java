package com.sklagat46.mcrop.views;

public class UserProfile {
    public String userProfileId;
    public String username;
    public String residence;
    public String phoneNumber;
    public String userEmail;
    public String password;

    public UserProfile(String userProfileId, String username,String residence,String phoneNumber, String userEmail,String password) {
        this.userProfileId =userProfileId;
        this.username=username;
        this.residence = residence;
        this.phoneNumber = phoneNumber;
        this.userEmail = userEmail;
        this.password = password;
    }
    public String getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(String userProfileId) {
        this.userProfileId=userProfileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username=username;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



