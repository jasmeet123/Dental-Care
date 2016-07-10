package com.firstopiniondentist.model;

import android.media.Image;

import java.util.ArrayList;

/**
 * Created by jasmeetsingh on 3/1/16.
 */
public class UserDentalRecord {

    public static final int MAX_UPLOAD_IMAGES = 5;
    private String dentalProblem;
    private ArrayList<Image> dentalImages;
    int status;

    public void createDentalRecord(String dentalProblem, ArrayList<Image> dentalImages){
        this.dentalProblem = dentalProblem;
        this.dentalImages = dentalImages;

    }



}
