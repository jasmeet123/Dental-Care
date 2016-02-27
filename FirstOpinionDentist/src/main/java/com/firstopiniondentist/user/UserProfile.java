package com.firstopiniondentist.user;

/**
 * Created by jasmeetsingh on 2/20/16.
 */
public class UserProfile {

    private static UserProfile profile;
    public  static UserProfile getInstance(){
        if(profile == null){
            profile = new UserProfile();
        }
        return profile;

    }
    private String id;
    private String login_type;
    private UserFBProfile fbProfile;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private UserLocation location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public UserFBProfile getFbProfile() {
        return fbProfile;
    }

    public void setFbProfile(UserFBProfile fbProfile) {
        this.fbProfile = fbProfile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }
}
