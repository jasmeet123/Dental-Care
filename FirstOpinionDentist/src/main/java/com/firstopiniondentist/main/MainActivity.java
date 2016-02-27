package com.firstopiniondentist.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.user.UserProfile;
import com.firstopiniondentist.util.GeneralUtil;

public class MainActivity extends Activity {
    CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private boolean isGoingToStart;

   private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {

                if(!isGoingToStart)
                    updateWithToken(newAccessToken);
            }
        };

        Log.i(TAG,"Access token tracking" + accessTokenTracker.isTracking());
        accessToken = AccessToken.getCurrentAccessToken();




    }



    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private void setCurrentActivity(){
        setContentView(R.layout.activity_main);

        loginButton  = (LoginButton)findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startLandingActivity(loginResult.getAccessToken());
                        // Create User profile
                        // send access token and other fb info to backend


                    }

                    @Override
                    public void onCancel() {
                        Log.i(TAG, "Login Cancel ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.i(TAG, "Login Error" + exception.toString());
                    }
                });


    }

    public void startLandingActivity(AccessToken loginResult){
        Intent startIntent= new Intent(getApplicationContext(), LandingActivity.class);
        startActivity(startIntent);
        finish();
    }

    private void createUserProfile(AccessToken result){
        UserProfile userProfile = UserProfile.getInstance();
        userProfile.setId(GeneralUtil.getId());
        userProfile.getFbProfile().setAccessToken(result.getToken());
        userProfile.getFbProfile().setApplicationId(result.getApplicationId());
        userProfile.getFbProfile().setUserId(result.getUserId());


    }

    public void updateWithToken(AccessToken currentAccessToken){
        if (currentAccessToken != null) {
            startLandingActivity(AccessToken.getCurrentAccessToken());
        } else {
            setCurrentActivity();
        }
    }
// FIXME: 2/20/16
    public void onStart(){
        super.onStart();

        if(accessToken != null){
            isGoingToStart = true;
            updateWithToken(accessToken);
        }


    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
    }


}
