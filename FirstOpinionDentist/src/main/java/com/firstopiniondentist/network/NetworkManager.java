package com.firstopiniondentist.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.storage.UserPrefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jasmeetsingh on 4/3/16.
 */
public class NetworkManager {

    private static NetworkManager networkManager;
    private String LOCAL_HOST = "http://10.0.0.163:8000/";
    private String PRODUCTION_HOST = "";
    private String PROFILE = "profile";
    private String SIGNUP = "facebook-signup/";
    private String DENTAL_LOGIN = "dental-login/";
    private String TIPS = "tips/";
    private String AUTH_TOKEN = "api-token-auth/";
    private String DENTAL_REQUEST = "request/";
    private String LOGOUT = "logout";
    private String DENTAL_HISTORY = "history";

    private static int MAX_RETRY = 3;
    private RequestQueue mRequestQueue;
    private Context mContext;
    private String mAppId;
    private static String TAG = "Network";
    public static NetworkManager getInstance(){
        if(networkManager == null){
            networkManager = new NetworkManager();
        }
        return networkManager;

    }
    public void initNetworkManager(Context context){
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);

    }


    private RequestQueue getQueue(){
        if(mRequestQueue != null){
            return getInstance().mRequestQueue;
        }else{
            mRequestQueue = Volley.newRequestQueue(mContext);
            return mRequestQueue;
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void dentalLogin(final String username, final String password, final NetworkRequest request){
        JSONObject profile = new JSONObject();
        try {
            profile.put("username",username);
            profile.put("password",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        int method = Request.Method.POST;
        String url = LOCAL_HOST + DENTAL_LOGIN;
        Log.i(TAG,"URL is" + url);

        JsonObjectRequest profileReq = new JsonObjectRequest(method, url, profile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                request.success(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",username,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;

            }

        };

        profileReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(profileReq);


    }

    public void getDentistAuthToken(final String username, final String password, final NetworkRequest request){
        JSONObject profile = new JSONObject();
        try {
            profile.put("username",username);
            profile.put("password",password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        int method = Request.Method.POST;
        String url = LOCAL_HOST + AUTH_TOKEN;
        Log.i(TAG,"URL is" + url);

        JsonObjectRequest profileReq = new JsonObjectRequest(method, url, profile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                request.success(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",username,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;

            }

        };

        profileReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(profileReq);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void postTip(final String username, final String password, final String tipTitle, final String tipDetail, final Uri tipImage, final NetworkRequest request){
        JSONObject tipObject = new JSONObject();
        try {
            tipObject.put("title",tipTitle);
            tipObject.put("desc",tipDetail);
            if(tipImage != null) {
                tipObject.put("image", tipImage.toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        int method = Request.Method.POST;
        String url = LOCAL_HOST + TIPS;
        Log.i(TAG,"URL is" + url);

        JsonObjectRequest profileReq = new JsonObjectRequest(method, url, tipObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                request.success(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",username,password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;

            }

        };

        profileReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(profileReq);


    }

    /**
     * Send the profile info to backend
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void sendProfileInfo(AccessToken accessToken, final NetworkRequest request){
        JSONObject profile = new JSONObject();
        try {
            profile.put("access_token",accessToken.getToken());
            profile.put("login_type",UserProfile.getInstance().getLoginType());
            profile.put("fbuserId",UserProfile.getInstance().getFbProfile().getUserId());
            profile.put("first_name",UserProfile.getInstance().getFirstName());
            profile.put("last_name",UserProfile.getInstance().getLastName());
            profile.put("gender",UserProfile.getInstance().getGender());
            profile.put("age",UserProfile.getInstance().getAge());
            profile.put("email",UserProfile.getInstance().getEmail());
            profile.put("city",UserProfile.getInstance().getLocation().getCity());
            profile.put("state",UserProfile.getInstance().getLocation().getState());
            profile.put("country",UserProfile.getInstance().getLocation().getCountry());
            profile.put("enable",true);
            profile.put("zip","94015");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        int method = Request.Method.POST;
        String url = LOCAL_HOST + SIGNUP;
        Log.i(TAG,"JSON is" + profile.toString());


        JsonObjectRequest profileReq = new JsonObjectRequest(method, url, profile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                request.success(response);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","admin","jasmeetadmin123");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }

        };

        profileReq.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(profileReq);


    }

    /**
     * Fetch the recent tips
     */
    public void fetchRecentTips(final NetworkRequest request){

        int method = Request.Method.GET;
        String url = LOCAL_HOST + TIPS;
        Log.i(TAG,"URL is" + url);

        JsonArrayRequest tipsRequest = new JsonArrayRequest(method, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG,response.toString());
                request.success(response);
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){


        };

        tipsRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(tipsRequest);

    }

    /**
     *
     */
    public void postDentalMessage(String detail, final NetworkRequest request){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("request_title","Dental Care App ");
            requestObject.put("request_desc",detail);
            requestObject.put("request_user", UserPrefs.getInstance().getEmail());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        int method = Request.Method.POST;
        String url = LOCAL_HOST + DENTAL_REQUEST ;
        Log.i(TAG,"URL is" + url);

        JsonObjectRequest message = new JsonObjectRequest(method, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG,response.toString());
                request.success(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
                request.failed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s","admin","jasmeetadmin123");
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;

            }

        };

        message.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, MAX_RETRY, 2.0f));
        getQueue().add(message);

    }

    public void postDentalHistory(){

    }

    public void logoutUser(){

    }

    public void updateUserSettings(){

    }



}
