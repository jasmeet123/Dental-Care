package com.firstopiniondentist.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.network.NetworkRequest;
import com.firstopiniondentist.storage.UserPrefs;
import com.firstopiniondentist.util.GeneralUtil;

import org.json.JSONObject;

public class MainActivity extends Activity {
    CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private boolean isGoingToStart;
    private TextView isDentistText;

   private static final String TAG = MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        UserPrefs.getInstance().init(getApplicationContext());
        NetworkManager.getInstance().initNetworkManager(getApplicationContext());
        UserPrefs.getInstance().updateUserModel();


            callbackManager = CallbackManager.Factory.create();
            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {


                    if (!isGoingToStart)
                        updateWithToken(newAccessToken);
                }
            };

            Log.i(TAG, "Access token tracking" + accessTokenTracker.isTracking());
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
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_about_me");
        loginButton.setReadPermissions("user_birthday");
        loginButton.setReadPermissions("user_location");
        isDentistText = (TextView)findViewById(R.id.is_dentist);
        isDentistText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),DentistLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        callGraphAPI(loginResult.getAccessToken());


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


    public void callGraphAPI(final AccessToken token){
        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject profileObj = response.getJSONObject();


                createUserProfile(token, object);
                NetworkManager.getInstance().sendProfileInfo(token, new NetworkRequest() {
                    @Override
                    public Object success(Object data) {
                        Intent startIntent= new Intent(getApplicationContext(), LandingActivity.class);
                        startIntent.putExtra(LandingActivity.USER_TYPE, com.firstopiniondentist.model.UserProfile.IS_PATIENT);
                        startActivity(startIntent);
                        finish();
                        return null;
                    }

                    @Override
                    public Object failed(Object data) {
                        Log.e(TAG, data.toString());
                        return null;
                    }
                });

            }
        });

        Bundle params = new Bundle();
        params.putString("fields","id,name,birthday,location,gender,email");
        request.setParameters(params);
        request.executeAsync();
    }

//    public void startLandingActivity(AccessToken loginResult){
//        Intent startIntent= new Intent(getApplicationContext(), LandingActivity.class);
//        startIntent.putExtra(LandingActivity.USER_TYPE, com.firstopiniondentist.model.UserProfile.IS_PATIENT);
//        startActivity(startIntent);
//        finish();
//    }

    private void createUserProfile(AccessToken result, JSONObject object){
        UserProfile userProfile = UserProfile.getInstance();
        userProfile.setId(GeneralUtil.getId());
        userProfile.getFbProfile().setAccessToken(result.getToken());
        userProfile.getFbProfile().setLinkId(object.optString("id"));
        String firstname = (object.optString("name").split("\\s+"))[0];
        if(firstname != null && !firstname.isEmpty()) {
            userProfile.setFirstName((object.optString("name").split("\\s+"))[0]);
        }else{
            userProfile.setFirstName("FNU");
        }
        userProfile.setLastName((object.optString("name").split("\\s+"))[1]);
        if(object.optJSONObject("location") != null) {
            userProfile.getLocation().setCity((object.optJSONObject("location").optString("name").split(","))[0]);
            userProfile.getLocation().setState((object.optJSONObject("location").optString("name").split(","))[1]);
            userProfile.getLocation().setCountry("USA");

        }
        userProfile.setGender(object.optString("gender"));
        userProfile.setEmail(object.optString("email"));
        userProfile.setLoginType(UserProfile.FACEBOOK);
        userProfile.setAge(object.optInt("age"));

        userProfile.getFbProfile().setApplicationId(result.getApplicationId());
        userProfile.getFbProfile().setUserId(result.getUserId());
        UserPrefs.getInstance().persistUserData();



    }



    public void updateWithToken(AccessToken currentAccessToken){
        if (currentAccessToken != null) {
            UserPrefs.getInstance().updateUserProfileFromPrefs();
            NetworkManager.getInstance().sendProfileInfo(currentAccessToken, new NetworkRequest() {
                @Override
                public Object success(Object data) {

                    Intent startIntent= new Intent(getApplicationContext(), LandingActivity.class);
                    startIntent.putExtra(LandingActivity.USER_TYPE, com.firstopiniondentist.model.UserProfile.IS_PATIENT);
                    startActivity(startIntent);
                    finish();
                    return null;
                }

                @Override
                public Object failed(Object data) {
                    Log.e(TAG, data.toString());
                    return null;
                }
            });
        } else {
            setCurrentActivity();
        }
    }
// FIXME: 2/20/16
    public void onStart(){
        super.onStart();
        if(UserPrefs.getInstance().isLoggedIn() &&  UserPrefs.getInstance().getLoggedInType().equals(com.firstopiniondentist.model.UserProfile.IS_DENTIST)){
            Intent i = new Intent(getApplicationContext(), LandingActivity.class);
            i.putExtra(LandingActivity.USER_TYPE, com.firstopiniondentist.model.UserProfile.IS_DENTIST);
            startActivity(i);

        }else {
            if (accessToken != null) {
                isGoingToStart = true;
                updateWithToken(accessToken);
            } else {
                setCurrentActivity();
            }
        }


    }



    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
    }





}
