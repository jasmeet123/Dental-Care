package com.firstopiniondentist.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.firstopiniondentist.model.UserProfile;

import static com.facebook.internal.FacebookRequestErrorClassification.KEY_NAME;

/**
 * Created by jasmeetsingh on 3/9/16.
 */
public class UserPrefs {
    private SharedPreferences mPrefs;

    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_TOKEN = "key_token";
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_LOGIN_TYPE = "key_login_type";
    private static final String KEY_FIRST_NAME = "key_first_name";
    private static final String KEY_LAST_NAME = "key_last_name";
    private static final String KEY_CITY = "key_city";
    private static final String KEY_STATE = "key_state";
    private static final String KEY_GENDER = "key_gender";
    private static final String KEY_PASSWORD = "key_pass";
    private static final String KEY_AGE = "key_age";
    private static final String KEY_FB_USER_ID = "key_FBuserId";
    private static final String KEY_Phone = "key_phone";
    private static final String KEY_LINK_ID = "key_link_id";
    private static final String KEY_IS_LOGGEDIN = "key_loggedin";
    private static final String KEY_ID  = "key_id";
    private static final String KEY_ZIP = "key_zip";

    private static final String KEY_EMAIL_DIALOG_CANCEL = "key_email_cancel";



    private static UserPrefs sInstance;

    // The name of the resulting SharedPreferences
    private static final String SHARED_PREFERENCE_NAME = UserProfile.class
            .getSimpleName();

    public static synchronized UserPrefs getInstance() {
        if (sInstance == null) {
            sInstance = new UserPrefs();
        }
        return sInstance;
    }

    private UserPrefs(){

    };




   public SharedPreferences getSharedPrefInstance() {

        return mPrefs;
    }

    public void init(Context context) {

        mPrefs = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
                context.MODE_PRIVATE);

    }

   public void persistUserData(){
       Editor editor = mPrefs.edit();
       editor.putString(KEY_EMAIL, UserProfile.getInstance().getEmail());
       editor.putString(KEY_FIRST_NAME, UserProfile.getInstance().getFirstName());
       editor.putString(KEY_LAST_NAME, UserProfile.getInstance().getLastName());
       editor.putInt(KEY_AGE, UserProfile.getInstance().getAge());
       editor.putString(KEY_CITY, UserProfile.getInstance().getLocation().getCity());
       editor.putString(KEY_STATE, UserProfile.getInstance().getLocation().getState());
       editor.putString(KEY_LINK_ID, UserProfile.getInstance().getFbProfile().getLinkId());
       editor.putString(KEY_ID, UserProfile.getInstance().getId());
       editor.putString(KEY_ZIP,UserProfile.getInstance().getLocation().getZipCode());
       editor.putString(KEY_FB_USER_ID, UserProfile.getInstance().getFbProfile().getUserId());
       editor.putString(KEY_GENDER, UserProfile.getInstance().getGender());
       editor.commit();

   }

    public String getFbUserId(){
        return mPrefs.getString(KEY_FB_USER_ID, UserProfile.getInstance().getFbProfile().getUserId());
    }
    public void persistCredentials(String username,String password,String token){
        Editor editor = mPrefs.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD,password);
        editor.putString(KEY_TOKEN,token);
        editor.commit();

    }

    public String getEmail(){
        return mPrefs.getString(KEY_EMAIL,UserProfile.getInstance().getEmail());
    }

    public void cancelEmail(Boolean cancel){
        Editor editor = mPrefs.edit();
        editor.putBoolean(KEY_EMAIL_DIALOG_CANCEL, cancel);
        editor.commit();
    }

    public boolean isEmailCanceled (){
        return mPrefs.getBoolean(KEY_EMAIL_DIALOG_CANCEL, false);
    }


    public void updateUserProfileFromPrefs(){
        UserProfile.getInstance().setFirstName(mPrefs.getString(KEY_FIRST_NAME,""));
        UserProfile.getInstance().setLastName(mPrefs.getString(KEY_LAST_NAME,""));
        UserProfile.getInstance().setLoginType(mPrefs.getString(KEY_LOGIN_TYPE,""));
        UserProfile.getInstance().getLocation().setCity(mPrefs.getString(KEY_CITY,""));
        UserProfile.getInstance().getLocation().setState(mPrefs.getString(KEY_STATE,""));
        UserProfile.getInstance().getLocation().setZipCode(mPrefs.getString(KEY_ZIP,""));
        UserProfile.getInstance().setAge(mPrefs.getInt(KEY_AGE,0));
        UserProfile.getInstance().setEmail(mPrefs.getString(KEY_EMAIL,""));
        UserProfile.getInstance().setGender(mPrefs.getString(KEY_GENDER,""));


    }

   public void putLoginType(String loginType){
       Editor editor = mPrefs.edit();
       editor.putString(KEY_LOGIN_TYPE, loginType);
       editor.commit();

   }

   public String getLoggedInType(){
       return mPrefs.getString(KEY_LOGIN_TYPE, UserProfile.getInstance().getUserType());
   }

    public String getUsername(){
        return mPrefs.getString(KEY_USERNAME, UserProfile.getInstance().getUserType());
    }

    public String getPassword(){
        return mPrefs.getString(KEY_PASSWORD, UserProfile.getInstance().getUserType());
    }

   public void putIsLoggedIn(Boolean islogin){
       Editor editor = mPrefs.edit();
       editor.putBoolean(KEY_IS_LOGGEDIN, islogin);
       editor.commit();
   }

   public boolean isLoggedIn(){
       return mPrefs.getBoolean(KEY_IS_LOGGEDIN, false);
   }

    public void updateUserModel(){
        com.firstopiniondentist.model.UserProfile.getInstance().setEmail(mPrefs.getString(KEY_EMAIL,""));
        com.firstopiniondentist.model.UserProfile.getInstance().setAge(mPrefs.getInt(KEY_AGE,0));
        com.firstopiniondentist.model.UserProfile.getInstance().setUserType(mPrefs.getString(KEY_LOGIN_TYPE, com.firstopiniondentist.model.UserProfile.IS_PATIENT));
        com.firstopiniondentist.model.UserProfile.getInstance().getFbProfile().setLinkId(mPrefs.getString(KEY_LINK_ID,""));
        com.firstopiniondentist.model.UserProfile.getInstance().setFirstName(mPrefs.getString(KEY_NAME,""));
        com.firstopiniondentist.model.UserProfile.getInstance().getLocation().setCity(mPrefs.getString(KEY_CITY,""));
        com.firstopiniondentist.model.UserProfile.getInstance().setId(mPrefs.getString(KEY_ID,""));
        com.firstopiniondentist.model.UserProfile.getInstance().setGender(mPrefs.getString(KEY_GENDER,"M"));

    }




}
