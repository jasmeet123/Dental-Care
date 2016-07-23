package com.firstopiniondentist.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.main.LandingActivity;
import com.firstopiniondentist.network.NetworkManager;
import com.firstopiniondentist.network.NetworkRequest;
import com.firstopiniondentist.storage.UserPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskDentistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskDentistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskDentistFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Spinner mTitleSpinner;
    private EditText requestBox;
    private Button submitButton;
    private int mStackLevel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String selectedTitle;
    private TextView askZip;
    private FrameLayout zipFrame;

    private OnFragmentInteractionListener mListener;

    public AskDentistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AskDentistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AskDentistFragment newInstance(String param1) {
        AskDentistFragment fragment = new AskDentistFragment();
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
        View v = inflater.inflate(R.layout.fragment_ask_dentist, container, false);
        askZip = (TextView)v.findViewById(R.id.ask_zip);
        zipFrame = (FrameLayout)v.findViewById(R.id.zip_frame);
        if(UserPrefs.getInstance().getZipCode()== null){
            askZip.setText("Please add zipcode in your profile to request to nearby dentist");
        }else{
            zipFrame.setVisibility(View.GONE);

        }
        mTitleSpinner = (Spinner)v.findViewById(R.id.request_title_spinner);
        initTitles(mTitleSpinner);
        mTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTitle = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
                requestBox = (EditText) v.findViewById(R.id.request_edit);
        submitButton = (Button)v.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        });

        return v;
    }

    private void initTitles(Spinner mTitleSpinner){
        List<String> titles = new ArrayList<String>();
        titles.add("General Checkup");
        titles.add("Braces Consultations");
        titles.add("Consmetic Consultation");
        titles.add("Teeth Problem");
        titles.add("Gum Problem");
        titles.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.preference_category,titles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTitleSpinner.setAdapter(dataAdapter);
        selectedTitle = mTitleSpinner.getSelectedItem().toString();
    }

    void showDialog() {
        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        LandingActivity.EmailDialogFragment newFragment = LandingActivity.EmailDialogFragment.newInstance(mStackLevel);

        newFragment.show(ft, "dialog");
    }

    public void postRequest(){

        if(UserPrefs.getInstance().getEmail() == null || UserPrefs.getInstance().getEmail().isEmpty()){
            showDialog();
            return;
        }
        String requestText = requestBox.getText().toString();
        if(requestText != null && !requestText.isEmpty()){

            NetworkManager.getInstance().postDentalMessage(selectedTitle, requestText, new NetworkRequest() {
                @Override
                public Object success(Object data) {
                    Toast.makeText(getContext(),"Your request has been submitted succesfully", Toast.LENGTH_SHORT).show();
                    requestBox.setText("");
                    return data;
                }

                @Override
                public Object failed(Object data) {
                    Toast.makeText(getContext(),"Failed to submit request", Toast.LENGTH_SHORT).show();
                    return data;
                }
            });
        }else{
            Toast.makeText(getContext(),"Please add some detail to submit your request",Toast.LENGTH_SHORT).show();
        }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
