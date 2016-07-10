package com.firstopiniondentist.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.fragments.UserRequestsFragment.OnListFragmentInteractionListener;
import com.firstopiniondentist.fragments.userrecord.UserContent.UserItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UserItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<UserItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<UserItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.userName.setText(mValues.get(position).name);
        holder.userDetail.setText(mValues.get(position).detail);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public  TextView userName;
        public  TextView userDetail;
        public  ImageView userImage;
        public UserItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            userName = (TextView) view.findViewById(R.id.user_name);
            userDetail = (TextView) view.findViewById(R.id.user_details);
            userImage = (ImageView)view.findViewById(R.id.user_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + userName.getText() + "'";
        }
    }
}
