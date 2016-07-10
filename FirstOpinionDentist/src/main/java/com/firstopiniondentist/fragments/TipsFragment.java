package com.firstopiniondentist.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.firstopiniondentist.adapters.TipAdapter;
import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.main.LandingActivity;
import com.firstopiniondentist.main.PostTipActivity;
import com.firstopiniondentist.model.Tip;
import com.firstopiniondentist.model.UserProfile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TipsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TipsFragment#} factory method to
 * create an instance of this fragment.
 */
public  class TipsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TipAdapter tipAdapter;
    private ListView tiplistView;

    public TipsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment TipsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TipsFragment getTipsFragment(String fragmentNumber) {

        TipsFragment fragment = new TipsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fragmentNumber);
         fragment.setArguments(args);
        return fragment;
    }

    public TipAdapter getTipAdapter(){
        if(tipAdapter == null){
            tipAdapter = new TipAdapter(((LandingActivity)getActivity()).getApplicationContext(), Tip.tipsList);
        }
        return tipAdapter;
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


       View tipsView = inflater.inflate(R.layout.fragment_tips, container, false);

        tiplistView = (ListView)tipsView.findViewById(R.id.tipList);
        tiplistView.setAdapter(getTipAdapter());

        Button postButton = (Button)tipsView.findViewById(R.id.post_button);
        if(UserProfile.getInstance().getUserType().equals(UserProfile.IS_DENTIST)){
            postButton.setVisibility(View.VISIBLE);
            postButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getContext(), PostTipActivity.class);
                    startActivity(i);
                }
            });
        }
        return tipsView;
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
