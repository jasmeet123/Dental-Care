package com.firstopiniondentist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.login.LoginManager;
import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.storage.UserPrefs;
import com.firstopiniondentist.util.GeneralUtil;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout logout;
    RelativeLayout supportLayout;
    RelativeLayout pastRequestLayout;
    RelativeLayout aboutLayout;
    RelativeLayout termsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.action_settings));
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbargradient));
        logout = (RelativeLayout)findViewById(R.id.logout_relativeLayout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                setResult(GeneralUtil.LOGOUT);
                UserPrefs.getInstance().putIsLoggedIn(false);
                NetworkManager.getInstance().logoutUser();
                finish();



            }
        });
        supportLayout = (RelativeLayout)findViewById(R.id.help_relative);
        supportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"support@dentalcareapp.com"});
                startActivity(Intent.createChooser(emailIntent, ""));

            }
        });
        pastRequestLayout = (RelativeLayout)findViewById(R.id.past_relative);
        pastRequestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PastRequestActivity.class);
                startActivity(i);
            }
        });

        aboutLayout = (RelativeLayout)findViewById(R.id.info_relative);
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(i);
            }
        });

        termsLayout = (RelativeLayout)findViewById(R.id.terms_relative);
        termsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(i);
            }
        });


    }
}
