package com.firstopiniondentist.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.main.LandingActivity;
import com.firstopiniondentist.main.PastRequestActivity;
import com.firstopiniondentist.model.UserProfile;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.network.NetworkRequest;
import com.firstopiniondentist.storage.UserPrefs;
import com.firstopiniondentist.util.GeneralUtil;

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
    private TextView nameText;
    private TextView genderText;
    private static TextView ageText;
    private TextView emailText;
    private static TextView zipText;
    private TextView requestHistory;

    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String UNDISCLOSED = "Undisclosed";

    private OnFragmentInteractionListener mListener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View profileView =  inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = (ImageView)profileView.findViewById(R.id.profile_image);
        nameText = (TextView)profileView.findViewById(R.id.name_text_val);
        genderText = (TextView)profileView.findViewById(R.id.gender_text_val);
        ageText = (TextView)profileView.findViewById(R.id.age_text_val);
        emailText = (TextView)profileView.findViewById(R.id.email_text_val);
        zipText = (TextView)profileView.findViewById(R.id.zip_text_val);
        requestHistory = (TextView)profileView.findViewById(R.id.dental_text);
        GeneralUtil.getFacebookProfilePicture(profileImage,UserProfile.getInstance().getFbProfile().getLinkId());
        nameText.setText(UserPrefs.getInstance().getFirstName() + " " + UserPrefs.getInstance().getLastName() );


        if(UserPrefs.getInstance().getGender() != null ) {
            if(UserPrefs.getInstance().getGender().equalsIgnoreCase("male")) {
                genderText.setText(MALE);
            }
            else
                genderText.setText(FEMALE);
        }else{
            genderText.setText(UNDISCLOSED);
        }

        if(UserPrefs.getInstance().getAge() != 0 ) {
            ageText.setText(UserPrefs.getInstance().getAge().toString());
        }else{
            ageText.setText(getResources().getText(R.string.add_age));
            ageText.setTextColor(Color.BLUE);
            ageText.setClickable(true);
            ageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    AgeDialogFragment newFragment = AgeDialogFragment.newInstance(0);

                    newFragment.show(ft, "dialog");
                }
            });
        }
        if(!UserPrefs.getInstance().getEmail().isEmpty()) {
            emailText.setText(UserPrefs.getInstance().getEmail());
        }else{
            emailText.setText(getResources().getText(R.string.add_email));
            emailText.setTextColor(Color.BLUE);
            emailText.setClickable(true);
            emailText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    LandingActivity.EmailDialogFragment newFragment = LandingActivity.EmailDialogFragment.newInstance(0);

                    newFragment.show(ft, "dialog");
                }
            });
        }
        if(UserPrefs.getInstance().getZipCode() != null) {
            zipText.setText(UserPrefs.getInstance().getZipCode());
        }else{
            zipText.setText(getResources().getText(R.string.add_zip));
            zipText.setTextColor(Color.BLUE);
            zipText.setClickable(true);
            zipText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    ZipDialogFragment newFragment = ZipDialogFragment.newInstance(0);

                    newFragment.show(ft, "dialog");
                }
            });
        }

        requestHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PastRequestActivity.class);
                startActivity(i);
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

    public static class AgeDialogFragment extends DialogFragment {
        /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        public static AgeDialogFragment newInstance(int num) {
            AgeDialogFragment f = new AgeDialogFragment();


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
            final EditText ageEdit = (EditText) v.findViewById(R.id.email_edit);
            ageEdit.setHint("Age");
            ageEdit.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

            final TextView titleText = (TextView) v.findViewById(R.id.email_title_text);
            titleText.setText("Please Enter Your Age");
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
                    int age = Integer.parseInt(ageEdit.getText().toString());
                    if (age > 0 ) {
                        UserProfile.getInstance().setAge(age);
                        UserPrefs.getInstance().setAge(age);
                        NetworkManager.getInstance().updateProfileInfo("age",new NetworkRequest() {
                            @Override
                            public Object success(Object data) {

                                ageText.setText(""+UserProfile.getInstance().getAge());
                                ageText.setTextColor(Color.BLACK);
                                ageText.setClickable(false);
                                return null;
                            }

                            @Override
                            public Object failed(Object data) {
                                return null;
                            }
                        });
                        getDialog().dismiss();
                    } else {
                        titleText.setTextColor(Color.RED);
                        titleText.setText("Invalid Age");
                    }
                }
            });



            return v;
        }

    }

    public static class ZipDialogFragment extends DialogFragment {
        /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        public static ZipDialogFragment newInstance(int num) {
            ZipDialogFragment f = new ZipDialogFragment();


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
            final EditText zipEdit = (EditText) v.findViewById(R.id.email_edit);
            zipEdit.setHint("Zip Code");
            zipEdit.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

            final TextView titleText = (TextView) v.findViewById(R.id.email_title_text);
            titleText.setText("Please Enter Your Zip Code");
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
                    final String zip = zipEdit.getText().toString();
                    if (zip != null && !zip.isEmpty()) {
                        UserProfile.getInstance().getLocation().setZipCode(zip);
                        UserPrefs.getInstance().setZipCode(zip);
                        NetworkManager.getInstance().updateProfileInfo( "zip", new NetworkRequest() {
                            @Override
                            public Object success(Object data) {

                                zipText.setTextColor(Color.BLACK);
                                zipText.setClickable(false);
                                zipText.setText(UserPrefs.getInstance().getZipCode());
                                return null;
                            }

                            @Override
                            public Object failed(Object data) {
                                return null;
                            }
                        });
                        getDialog().dismiss();
                    } else {
                        titleText.setTextColor(Color.RED);
                        titleText.setText("Invalid zip");
                    }
                }
            });



            return v;
        }

    }
}
