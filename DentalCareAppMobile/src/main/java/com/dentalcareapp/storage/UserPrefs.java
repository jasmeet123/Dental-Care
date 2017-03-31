package com.dentalcareapp.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dentalcareapp.model.UserProfile;

/**
 * Created by jasmeetsingh on 3/9/16.
 */
public class UserPrefs {
    private SharedPreferences mPrefs;
   // private static final String KEY_SERVER_ID = "key_id";

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
    private static final String KEY_TIMESTAMP = "key_time";

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
       if(UserProfile.getInstance().getId() != null && !UserProfile.getInstance().getId().isEmpty())
            editor.putString(KEY_ID, UserProfile.getInstance().getId());
       if(UserProfile.getInstance().getEmail() != null && !UserProfile.getInstance().getEmail().isEmpty())
            editor.putString(KEY_EMAIL, UserProfile.getInstance().getEmail());
       if(UserProfile.getInstance().getFirstName() != null && !UserProfile.getInstance().getFirstName().isEmpty())
            editor.putString(KEY_FIRST_NAME, UserProfile.getInstance().getFirstName());
       if(UserProfile.getInstance().getLastName() != null && !UserProfile.getInstance().getLastName().isEmpty())
             editor.putString(KEY_LAST_NAME, UserProfile.getInstance().getLastName());
       editor.putInt(KEY_AGE, UserProfile.getInstance().getAge());
       editor.putString(KEY_CITY, UserProfile.getInstance().getLocation().getCity());
       editor.putString(KEY_STATE, UserProfile.getInstance().getLocation().getState());
       editor.putString(KEY_LINK_ID, UserProfile.getInstance().getFbProfile().getLinkId());
       editor.putString(KEY_ZIP,UserProfile.getInstance().getLocation().getZipCode());
       if(UserProfile.getInstance().getFbProfile().getUserId() != null && !UserProfile.getInstance().getFbProfile().getUserId().isEmpty())
         editor.putString(KEY_FB_USER_ID, UserProfile.getInstance().getFbProfile().getUserId());
       editor.putString(KEY_GENDER, UserProfile.getInstance().getGender());
       editor.commit();

   }

    public String getServerId(){
        return mPrefs.getString(KEY_ID, UserProfile.getInstance().getId());
    }

    public String getFirstName(){
        return mPrefs.getString(KEY_FIRST_NAME, UserProfile.getInstance().getFirstName());
    }

    public String getLastName(){
        return mPrefs.getString(KEY_LAST_NAME, UserProfile.getInstance().getLastName());
    }

    public String getZipCode(){
        return mPrefs.getString(KEY_ZIP, UserProfile.getInstance().getLocation().getZipCode());
    }

    public Integer getAge(){
        return mPrefs.getInt(KEY_AGE, UserProfile.getInstance().getAge());
    }
    public String getGender(){
        return mPrefs.getString(KEY_GENDER, UserProfile.getInstance().getGender());
    }

    public void setAge(int age){
        Editor editor = mPrefs.edit();
        editor.putInt(KEY_AGE, age);
        editor.commit();
    }


    public void setServerId(String id){
        Editor editor = mPrefs.edit();
        editor.putString(KEY_ID, id);
        editor.commit();
    }


    public void setZipCode(String zip){
        Editor editor = mPrefs.edit();
        editor.putString(KEY_ZIP, zip);
        editor.commit();
    }

    public void setEmail(String email){
        Editor editor = mPrefs.edit();
        editor.putString(KEY_EMAIL, email);
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

    public void setTimeStamp(long timestamp){
        Editor editor = mPrefs.edit();
        editor.putLong(KEY_TIMESTAMP, timestamp);
        editor.commit();

    }
    public long getTimeStamp(){
       return  mPrefs.getLong(KEY_TIMESTAMP, System.currentTimeMillis());

    }

    public String getAuthToken(){
        return mPrefs.getString(KEY_TOKEN,"");
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
        UserProfile.getInstance().setEmail(mPrefs.getString(KEY_EMAIL,""));
        UserProfile.getInstance().setAge(mPrefs.getInt(KEY_AGE,0));
        UserProfile.getInstance().setUserType(mPrefs.getString(KEY_LOGIN_TYPE, UserProfile.IS_PATIENT));
        UserProfile.getInstance().getFbProfile().setLinkId(mPrefs.getString(KEY_LINK_ID,""));
        UserProfile.getInstance().setFirstName(mPrefs.getString(KEY_FIRST_NAME,""));
        UserProfile.getInstance().setFirstName(mPrefs.getString(KEY_LAST_NAME,""));
        UserProfile.getInstance().getLocation().setCity(mPrefs.getString(KEY_CITY,""));
        UserProfile.getInstance().setServerId(mPrefs.getString(KEY_ID,""));
        UserProfile.getInstance().setGender(mPrefs.getString(KEY_GENDER,"M"));

    }




}
