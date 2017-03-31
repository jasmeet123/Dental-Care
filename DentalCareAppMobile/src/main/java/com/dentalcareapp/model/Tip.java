package com.dentalcareapp.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by jasmeetsingh on 3/1/16.
 */
public class Tip {


    private Bitmap tipImage;
    private String tipImageUrl;
    private String tipType;
    private String tipTitle;

    public String getTipDesc() {
        return tipDesc;
    }

    public void setTipDesc(String tipDesc) {
        this.tipDesc = tipDesc;
    }

    public Bitmap getTipImage() {
        return tipImage;
    }

    public void setTipImage(Bitmap tipImage) {
        this.tipImage = tipImage;
    }

    public String getTipImageUrl() {
        return tipImageUrl;
    }

    public void setTipImageUrl(String tipImageUrl) {
        this.tipImageUrl = tipImageUrl;
    }

    public String getTipType() {
        return tipType;
    }

    public void setTipType(String tipType) {
        this.tipType = tipType;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tipTitle) {
        this.tipTitle = tipTitle;
    }

    public String getTipShortDesc() {
        return tipShortDesc;
    }

    public void setTipShortDesc(String tipShortDesc) {
        this.tipShortDesc = tipShortDesc;
    }

    private String tipDesc;
    private String tipShortDesc;



    public static ArrayList<Tip> tipsList = new ArrayList<Tip>();

    public static void addTip(String tipTitle, String tipDesc,String tipImageUrl){
        Tip tip = new Tip(tipTitle,tipDesc,tipImageUrl);
        tipsList.add(tip);

    }

    public static void removeTip(int index){
        tipsList.remove(index);

    }

    public static void removeAllTips(){
        tipsList.clear();

    }



    private Tip(Bitmap tipImage, String tipType, String tipTitle, String tipDesc){
        this.tipImage  = tipImage;
        this.tipType = tipType;
        this.tipTitle = tipTitle;
        this.tipDesc = tipDesc;
    }

    private Tip( String tipTitle, String tipDesc,String tipImageUrl){
        this.tipImageUrl  = tipImageUrl;
        this.tipType = tipType;
        this.tipTitle = tipTitle;
        this.tipDesc = tipDesc;
    }





}
