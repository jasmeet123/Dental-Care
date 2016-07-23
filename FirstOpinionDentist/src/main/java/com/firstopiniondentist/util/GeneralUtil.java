package com.firstopiniondentist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.model.Tip;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.storage.UserPrefs;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.UUID;

/**
 * Created by jasmeetsingh on 2/20/16.
 */
public class GeneralUtil {

    // Code
    public static final int LOGOUT = -1;
    public static final int DELETE = -999;

    public static String getId(){
        return UUID.randomUUID().toString();
    }


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void getFacebookProfilePicture(ImageView profileImage,String userID){
        if(UserProfile.getInstance().getProfileImage() != null){
            profileImage.setImageBitmap(UserProfile.getInstance().getProfileImage());
        }else {
            new ImageDownloader(profileImage).execute(userID);
        }


    }

    public static void loadTipImage(ImageView imageView,String url, int position){
        new TipImageDownloader(imageView, position).execute(url);
    }

    private static class TipImageDownloader extends AsyncTask<String,Integer,Bitmap>{

        private final WeakReference<ImageView> imageViewWeakReference;

        int position;
        TipImageDownloader (ImageView imageView, int pos){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
            position = pos;
        }

        @Override
        protected Bitmap doInBackground(String[] params) {
            Bitmap bitmap = null;
            try {
                URL imageURL = new URL(params[0]);
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(isCancelled()){
                bitmap = null;
            }
            if(imageViewWeakReference != null){
                ImageView imageView = imageViewWeakReference.get();
                if(imageView != null){
                    if(bitmap != null){
                        imageView.setImageBitmap(bitmap);
                        Tip.tipsList.get(position).setTipImage(bitmap);
                    }else{
                        imageView.setImageDrawable(imageView.getContext().getDrawable(R.drawable.tooth));
                    }
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer [] values) {
            super.onProgressUpdate(values);
        }
    }

    public static boolean isApplicationOpenedAfter1day(){
        long currentTimeMillis = System.currentTimeMillis();
        long timespan = (currentTimeMillis - UserPrefs.getInstance().getTimeStamp());
        return timespan > 24*60*3600;

    }


    private static class ImageDownloader extends AsyncTask<String,Integer,Bitmap>{

        private ImageView profileImage;
        ImageDownloader (ImageView image){
            profileImage = image;
        }

        @Override
        protected Bitmap doInBackground(String[] params) {
            Bitmap bitmap = null;
            try {
                URL imageURL = new URL("https://graph.facebook.com/" + params[0] + "/picture?type=large");
                bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            }catch (Exception e){
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            UserProfile.getInstance().setProfileImage(bitmap);
            profileImage.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer [] values) {
            super.onProgressUpdate(values);
        }
    }



}
