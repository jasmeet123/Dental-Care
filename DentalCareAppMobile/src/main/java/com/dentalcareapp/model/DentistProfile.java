package com.dentalcareapp.model;

/**
 * Created by jasmeetsingh on 7/2/16.
 */

public class DentistProfile extends CommonProfile {


    private String phoneNumber;
    private String userName;
    private String password;

    private static DentistProfile profile;

    public  static DentistProfile createDentist(String userName, String password){
        if(profile == null){
            profile = new DentistProfile(userName,password);

        }

        return profile;

    }

    public static DentistProfile getProfile(){
       if(profile == null){
           profile = new DentistProfile();
       }
        return profile;
    }

    private DentistProfile(){

    }

    private DentistProfile(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    private String isActive;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String authToken;

    public String getYearsOfExp() {
        return yearsOfExp;
    }

    public void setYearsOfExp(String yearsOfExp) {
        this.yearsOfExp = yearsOfExp;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    private String yearsOfExp;
    private String qualification;
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
