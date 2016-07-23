package com.firstopiniondentist.model;

import java.util.ArrayList;


/**
 * Created by jasmeetsingh on 3/1/16.
 */
public class UserDentalRequests {

    public static final int MAX_UPLOAD_IMAGES = 5;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String title;
    private String desc;
    public static ArrayList<UserDentalRequests> dentalRequests = new ArrayList<>();



    private UserDentalRequests(String title, String  desc){
        this.title = title;
        this.desc = desc;

    }

    public static void addDentalRequest(String title, String desc){
       UserDentalRequests request = new UserDentalRequests(title,desc);
        dentalRequests.add(request);

    }

    public static void clearDentalRequest(String title, String desc){
        UserDentalRequests request = new UserDentalRequests(title,desc);
        dentalRequests.clear();

    }



}
