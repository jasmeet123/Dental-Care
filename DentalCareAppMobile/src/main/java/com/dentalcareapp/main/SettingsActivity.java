package com.dentalcareapp.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;

import com.dentalcareapp.firstopiniondentist.R;
import com.dentalcareapp.network.NetworkManager;
import com.dentalcareapp.storage.UserPrefs;
import com.dentalcareapp.util.GeneralUtil;
import com.facebook.login.LoginManager;

public class SettingsActivity extends AppCompatActivity {

    RelativeLayout logout;
    RelativeLayout supportLayout;
    RelativeLayout pastRequestLayout;
    RelativeLayout aboutLayout;
    RelativeLayout termsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getResources().getString(R.string.action_settings);
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        setContentView(R.layout.activity_settings);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
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
//        pastRequestLayout = (RelativeLayout)findViewById(R.id.past_relative);
//        pastRequestLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), PastRequestActivity.class);
//                startActivity(i);
//            }
//        });

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
