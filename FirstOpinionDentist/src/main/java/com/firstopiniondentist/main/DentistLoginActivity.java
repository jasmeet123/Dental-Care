package com.firstopiniondentist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.model.DentistProfile;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.network.NetworkRequest;
import com.firstopiniondentist.storage.UserPrefs;

import org.json.JSONObject;

public class DentistLoginActivity extends ActionBarActivity {

    private TextView usernameText;
    private TextView passwordText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist_login);
        usernameText = (TextView)findViewById(R.id.dentist_username);
        passwordText = (TextView)findViewById(R.id.dental_password);
        loginButton = (Button)findViewById(R.id.dental_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }


    private void doLogin(){
        final String password = passwordText.getText().toString();
        final String username = usernameText.getText().toString();
       if(!password.isEmpty() && !username.isEmpty()){
           NetworkManager.getInstance().dentalLogin(username, password, new NetworkRequest() {
               @Override
               public Object success(Object data) {
                   createDentist(username,password);
                   getDentalToken(username,password);
                   Intent i = new Intent(getApplicationContext(), LandingActivity.class);
                   i.putExtra(LandingActivity.USER_TYPE, UserProfile.IS_DENTIST);
                   startActivity(i);
                   finish();
                   return true;
               }

               @Override
               public Object failed(Object data) {
                   Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                   return false;
               }
           });

       }else{
           Toast.makeText(getApplicationContext(),"Please check username or password",Toast.LENGTH_SHORT).show();

       }

    }

    private void createDentist(String username, String password){
        DentistProfile.createDentist(username,password);
    }

    private void getDentalToken(final String username, final String password){
        NetworkManager.getInstance().getDentistAuthToken(username, password, new NetworkRequest() {
            @Override
            public Object success(Object data) {
                if (data instanceof JSONObject){
                    JSONObject auth = (JSONObject)data;
                    String token = auth.optString("token");
                    DentistProfile.getProfile().setAuthToken(token);
                    UserPrefs.getInstance().persistCredentials(username,password,token);


                }
                return true;
            }

            @Override
            public Object failed(Object data) {
                Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public void onBackPressed(){
    super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
