package com.firstopiniondentist.model;

/**
 * Created by jasmeetsingh on 2/20/16.
 */
public class UserLocation {
    private Long currentLat;
    private Long currentLng;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private String state;
    private String country;
    private String zipCode;

    public Long getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(Long currentLat) {
        this.currentLat = currentLat;
    }

    public Long getCurrentLng() {
        return currentLng;
    }

    public void setCurrentLng(Long currentLng) {
        this.currentLng = currentLng;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
