package com.dentalcareapp.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dentalcareapp.adapters.RequestAdapter;
import com.dentalcareapp.firstopiniondentist.R;
import com.dentalcareapp.model.UserDentalRequests;
import com.dentalcareapp.model.UserProfile;
import com.dentalcareapp.network.NetworkManager;
import com.dentalcareapp.network.NetworkRequest;
import com.dentalcareapp.storage.UserPrefs;
import com.dentalcareapp.util.GeneralUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView profileImage;
    private EditText profileNameEdit;
    private EditText profileEmailEdit;
    private EditText profileAgeEdit;
    private EditText profileCityEdit;

    private ListView mRequestListView;

    private TextView requestHistory;

    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String UNDISCLOSED = "Undisclosed";

    private RequestAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private String emailBeforeEdit;
    private String ageBeforeEdit;
    private String cityBeforeEdit;
    private TextView noRequestText;
    private boolean isProfileGood;

    public void edit(){
        enableTextFields();
        emailBeforeEdit = profileEmailEdit.getText().toString();
        ageBeforeEdit = profileAgeEdit.getText().toString();
        cityBeforeEdit = profileCityEdit.getText().toString();
        if((profileEmailEdit.getText().toString().isEmpty())){
            profileEmailEdit.setClickable(true);
        }
        else if(!GeneralUtil.isValidEmailAddress(profileEmailEdit.getText().toString())){
            profileEmailEdit.setClickable(true);
        }else
            profileEmailEdit.setClickable(false);



    }



    public boolean save(){


        if(GeneralUtil.isValidEmailAddress(profileEmailEdit.getText().toString()) ){

            if(!profileEmailEdit.getText().toString().equals(emailBeforeEdit)) {
                UserPrefs.getInstance().setEmail(profileEmailEdit.getText().toString());
                UserProfile.getInstance().setEmail(profileEmailEdit.getText().toString());
                JSONObject profile = new JSONObject();
                try {
                    profile.put("email",profileEmailEdit.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkManager.getInstance().updateProfileInfo(profile, new NetworkRequest() {
                    @Override
                    public Object success(Object data) {
                        return null;
                    }

                    @Override
                    public Object failed(Object data) {
                        return null;
                    }
                });

            }

        }else{
            Toast.makeText(getContext(),getText(R.string.email_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }




        if(GeneralUtil.isInteger(profileAgeEdit.getText().toString(),10)) {
            if(!profileAgeEdit.getText().toString().equals(ageBeforeEdit)) {
                UserPrefs.getInstance().setAge(Integer.parseInt(profileAgeEdit.getText().toString()));
                profileAgeEdit.setTextColor(Color.WHITE);
                JSONObject profile = new JSONObject();
                try {
                    profile.put("age",profileAgeEdit.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NetworkManager.getInstance().updateProfileInfo(profile, new NetworkRequest() {
                    @Override
                    public Object success(Object data) {
                        return null;
                    }

                    @Override
                    public Object failed(Object data) {
                        return null;
                    }
                });


            }

        }else{
            Toast.makeText(getContext(),getText(R.string.age_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }

        disableTextFields();
        return true;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void disableTextFields(){
        profileNameEdit.setClickable(false);
        profileNameEdit.setEnabled(false);

        profileEmailEdit.setClickable(false);
        profileEmailEdit.setEnabled(false);

        profileAgeEdit.setClickable(false);
        profileAgeEdit.setEnabled(false);

        profileCityEdit.setClickable(false);
        profileCityEdit.setEnabled(false);
    }

    // We dont need to enable name field
    public void enableTextFields(){
        profileEmailEdit.setClickable(true);
        profileEmailEdit.setEnabled(true);

        profileAgeEdit.setClickable(true);
        profileAgeEdit.setEnabled(true);

        profileCityEdit.setClickable(true);
        profileCityEdit.setEnabled(true);
    }


    public void invalidateRequestList(){
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView =  inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = (ImageView)profileView.findViewById(R.id.profile_image);
        profileNameEdit = (EditText) profileView.findViewById(R.id.profile_name_edit);
        profileAgeEdit = (EditText) profileView.findViewById(R.id.profile_age_edit);
        profileEmailEdit = (EditText)profileView.findViewById(R.id.profile_email_edit);
        profileCityEdit = (EditText) profileView.findViewById(R.id.profile_city_edit);
        mRequestListView = (ListView)profileView.findViewById(R.id.profile_history_list);
        noRequestText = (TextView)profileView.findViewById(R.id.profile_no_request);

        //requestHistory = (TextView)profileView.findViewById(R.id.dental_text);

        GeneralUtil.getFacebookProfilePicture(profileImage, UserProfile.getInstance().getFbProfile().getLinkId());
        profileNameEdit.setText(UserPrefs.getInstance().getFirstName() + " " + UserPrefs.getInstance().getLastName() );
        profileNameEdit.setClickable(false);
        String city = UserProfile.getInstance().getLocation().getCity();
        profileCityEdit.setText(city);


        if(UserPrefs.getInstance().getAge() != 0 ) {
            profileAgeEdit.setText(UserPrefs.getInstance().getAge().toString());
        }else{
            profileAgeEdit.setHint(getResources().getText(R.string.add_age));
            profileAgeEdit.setTextColor(Color.WHITE);


        }

        if(!UserPrefs.getInstance().getEmail().isEmpty()) {
            profileEmailEdit.setText(UserPrefs.getInstance().getEmail());
        }else{
            profileEmailEdit.setHint(getResources().getText(R.string.add_email));
            profileEmailEdit.setTextColor(Color.WHITE);


        }
       disableTextFields();

        adapter = new RequestAdapter(getActivity(), UserDentalRequests.dentalRequests);
        mRequestListView.setAdapter(adapter);
        NetworkManager.getInstance().getUserRequests(new NetworkRequest() {
            @Override
            public Object success(Object data) {
                JSONArray requestarray = (JSONArray)data;
                if (requestarray.length() != 0){
                    noRequestText.setVisibility(View.GONE);
                    mRequestListView.setVisibility(View.VISIBLE);
                    UserDentalRequests.dentalRequests.clear();

                    mRequestListView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < requestarray.length(); i++) {
                        try {
                            JSONObject tipObj = requestarray.getJSONObject(i);
                            String title = tipObj.optString("request_title");
                            String desc = tipObj.optString("request_desc");
                            UserDentalRequests.addDentalRequest(title, desc);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    adapter.notifyDataSetChanged();

                }else{
                    noRequestText.setVisibility(View.VISIBLE);
                    mRequestListView.setVisibility(View.GONE);
                }
                return null;
            }


            @Override
            public Object failed(Object data) {
                return null;
            }
        });


        return profileView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}
