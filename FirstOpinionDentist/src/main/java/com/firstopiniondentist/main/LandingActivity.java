package com.firstopiniondentist.main;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.fragments.AskDentistFragment;
import com.firstopiniondentist.fragments.ProfileFragment;
import com.firstopiniondentist.fragments.TipsFragment;
import com.firstopiniondentist.fragments.UserRequestsFragment;
import com.firstopiniondentist.fragments.userrecord.UserContent;
import com.firstopiniondentist.model.Tip;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.network.NetworkRequest;
import com.firstopiniondentist.storage.UserPrefs;
import com.firstopiniondentist.util.GeneralUtil;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.firstopiniondentist.fragments.TipsFragment.getTipsFragment;

public class LandingActivity<T extends Fragment> extends ActionBarActivity implements ActionBar.TabListener, ProfileFragment.OnFragmentInteractionListener, TipsFragment.OnFragmentInteractionListener, UserRequestsFragment.OnListFragmentInteractionListener,
        AskDentistFragment.OnFragmentInteractionListener {


    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public static final String USER_TYPE = "user_type";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

//    private final Activity mActivity;
//    private final String mTag;
//    private final Class<T> mClass;
//    private final Bundle mArgs;
//    private Fragment mFragment;

//    public LandingActivity(Activity activity, String tag, Class<T> clz) {
//        this(activity, tag, clz, null);
//    }
//
//    public LandingActivity(Activity activity, String tag, Class<T> clz, Bundle args) {
//        mActivity = activity;
//        mTag = tag;
//        mClass = clz;
//        mArgs = args;
//
//        // Check to see if we already have a fragment for this tab, probably
//        // from a previously saved state.  If so, deactivate it, because our
//        // initial state is that a tab isn't shown.
//        mFragment = getSupportFragmentManager().findFragmentByTag(mTag);
//        if (mFragment != null && !mFragment.isDetached()) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.hide(mFragment);
//            ft.commit();
//        }
//    }


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private int mStackLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        setTitle(getString(R.string.title));
        UserProfile.getInstance().setUserType(getIntent().getStringExtra(USER_TYPE));
        UserPrefs.getInstance().putLoginType(UserProfile.getInstance().getUserType());
        UserPrefs.getInstance().putIsLoggedIn(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when

            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        if (UserPrefs.getInstance().getEmail().isEmpty() && !UserPrefs.getInstance().isEmailCanceled())
            showDialog();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        EmailDialogFragment newFragment = EmailDialogFragment.newInstance(mStackLevel);

        newFragment.show(ft, "dialog");
    }

    protected void initialize() {

        NetworkManager.getInstance().fetchRecentTips(new NetworkRequest() {
            @Override
            public Object success(Object data) {
                if (data instanceof JSONArray) {
                    JSONArray tipsArray = (JSONArray) data;
                    Tip.removeAllTips();
                    for (int i = 0; i < tipsArray.length(); i++) {
                        try {
                            JSONObject tipObj = tipsArray.getJSONObject(i);
                            String title = tipObj.optString("title");
                            String desc = tipObj.optString("desc");
                            String imageUrl = tipObj.optString("image");
                            Tip.addTip(title, desc, imageUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    Log.i("Tips", "Size of tips object" + Tip.tipsList.size());
                    // ((TipsFragment)getFragment("tips",0)).getTipAdapter().notifyDataSetChanged();

                }
                return null;
            }

            @Override
            public Object failed(Object data) {
                return null;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivityForResult(settingsIntent, 0);
            return true;
        } else if (id == R.id.notification) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == GeneralUtil.LOGOUT) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
            finish();
        }


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.


        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(UserContent.UserItem item) {

    }

    public Fragment getFragment(String type, int position) {
        Fragment fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = getSupportFragmentManager().findFragmentByTag(type);
        if (fragment == null) {
            switch (position) {

                case 0: {
                    fragment = getTipsFragment(type);
                    //    initialize();
                    break;

                }

                case 1: {
                    if (UserProfile.getInstance().getUserType().equals(UserProfile.IS_DENTIST)) {
                        fragment = UserRequestsFragment.newInstance(1);
                    } else {
                        fragment = AskDentistFragment.newInstance(type);
                    }
                    break;

                }
                case 2:
                    fragment = ProfileFragment.newInstance(type);
                    break;
                default:
                    fragment = TipsFragment.getTipsFragment(type);
                    break;
            }
//            ft.remove(fragment);
//            ft.add(fragment,type);
//            ft.commit();

            return fragment;
        }
        return fragment;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Landing Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                return getFragment("tips", position);
            } else if (position == 1) {
                return getFragment("askDentist", position);
            } else if (position == 2) {
                return getFragment("profile", position);
            }
            return getFragment("tips", position);

//            switch (position){
//
//                case 0 : {
//                    Fragment tipsFragment = getTipsFragment("tips");
//
//                    return tipsFragment;
//                }
//
//                case 1 : {
//                    if(UserProfile.getInstance().getUserType().equals(UserProfile.IS_DENTIST)){
//                        return UserRequestsFragment.newInstance(1);
//                    }else{
//                        return AskDentistFragment.newInstance("askDentist");
//                    }
//
//                    }
//                case 2 : return ProfileFragment.newInstance("profile");
//                default:
//                    return getTipsFragment("tips");
//            }


        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tips);
                case 1: {
                    if (UserProfile.getInstance().getUserType().equals(UserProfile.IS_DENTIST)) {
                        return getResources().getString(R.string.dental_requests);
                    } else {
                        return getResources().getString(R.string.ask);
                    }

                }
                case 2:
                    return getResources().getString(R.string.profile);
            }
            return null;
        }
    }

    public static class EmailDialogFragment extends DialogFragment {
        /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        public static EmailDialogFragment newInstance(int num) {
            EmailDialogFragment f = new EmailDialogFragment();


            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            // Pick a style based on the num.
            int style = DialogFragment.STYLE_NO_TITLE, theme = android.R.style.Theme_Holo_Light_Dialog;
            setStyle(style, theme);
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_email_dialog, container, false);
            final EditText emailEdit = (EditText) v.findViewById(R.id.email_edit);
            final TextView titleText = (TextView) v.findViewById(R.id.email_title_text);
            Button cancelButton = (Button) v.findViewById(R.id.cancel_button);
            Button doneButton = (Button) v.findViewById(R.id.done_button);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                    UserPrefs.getInstance().cancelEmail(true);
                }
            });

            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = emailEdit.getText().toString();
                    if (email != null && !email.isEmpty() && GeneralUtil.isValidEmailAddress(email)) {
                        UserProfile.getInstance().setEmail(email);
                        UserPrefs.getInstance().persistUserData();
                        getDialog().dismiss();
                    } else {
                        titleText.setTextColor(Color.RED);
                        titleText.setText("Invalid email");
                    }
                }
            });

            // Watch for button clicks.
//            Button button = (Button)v.findViewById(R.id.show);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            } {
//                public void onClick(View v) {
//                    // When button is clicked, call up to owning activity.
//
//                }
//            });

            return v;
        }

    }

}
