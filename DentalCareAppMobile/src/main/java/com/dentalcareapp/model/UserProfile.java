package com.dentalcareapp.model;

import java.util.ArrayList;

/**
 * Created by jasmeetsingh on 2/20/16.
 */
public class UserProfile extends CommonProfile{

    private static UserProfile profile;


    public static final String FACEBOOK = "facebook";
    public static final String GOOGLE = "google";
    public static final String EMAIL = "email";

    public  static UserProfile getInstance(){
        if(profile == null){
            profile = new UserProfile();

        }

        return profile;

    }

    private String id;


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    private String loginType;
    private UserFBProfile fbProfile;


    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    private String birthdate;


    private UserLocation location;
    private ArrayList<UserDentalRequests> dentalRecords;

    UserProfile(){
        dentalRecords = new ArrayList<UserDentalRequests>();
        setUserType(IS_PATIENT);
    }

    public String getId() {
        return id;
    }

    public void setServerId(String id) {
        this.id = id;
    }

    public UserFBProfile getFbProfile() {
        if(fbProfile == null) {
            fbProfile = new UserFBProfile();

        }
        return fbProfile;
    }

    public void setFbProfile(UserFBProfile fbProfile) {
        this.fbProfile = fbProfile;
    }

    public UserLocation getLocation() {
        if(location == null){
            location = new UserLocation();
        }
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }
}
