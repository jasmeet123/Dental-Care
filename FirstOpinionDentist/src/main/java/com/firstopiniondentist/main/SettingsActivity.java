package com.firstopiniondentist.main;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.login.LoginManager;
import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.storage.UserPrefs;
import com.firstopiniondentist.util.GeneralUtil;

public class SettingsActivity extends ActionBarActivity {

    RelativeLayout logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.action_settings));
        setContentView(R.layout.activity_settings);
        logout = (RelativeLayout)findViewById(R.id.logout_relativeLayout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                setResult(GeneralUtil.LOGOUT);
                UserPrefs.getInstance().putIsLoggedIn(false);
                finish();


            }
        });
    }
}
