package com.dentalcareapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dentalcareapp.firstopiniondentist.R;
import com.dentalcareapp.model.UserDentalRequests;

import java.util.ArrayList;

/**
 * Created by jasmeetsingh on 7/20/16.
 */

public class RequestAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<UserDentalRequests> mRequestList;
    private LayoutInflater mLayoutInflater;
    public RequestAdapter(Context context, ArrayList<UserDentalRequests> requestList) {
        mContext = context;
        mRequestList = requestList;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView requestTitle;
        public TextView requestDesc;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            requestTitle = (TextView) view.findViewById(R.id.history_title_text);
            requestDesc = (TextView) view.findViewById(R.id.history_desc);
        }


    }
    @Override
    public int getCount() {
        return mRequestList.size();
    }

    @Override
    public Object getItem(int i) {
        return mRequestList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        ViewHolder holder;
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.request_item, viewGroup, false);

            holder = new ViewHolder(convertView);
            v = convertView;
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();

        }
        holder.requestTitle.setText(mRequestList.get(position).getTitle());
        if(mRequestList.get(position).getTitle().equalsIgnoreCase("Gum Problem")){
            holder.requestTitle.setTextColor(Color.parseColor("#9E1313"));
        }else if (mRequestList.get(position).getTitle().equalsIgnoreCase("Teeth Problem")){
            holder.requestTitle.setTextColor(Color.parseColor("#1460B8"));
        }else if (mRequestList.get(position).getTitle().equalsIgnoreCase("General Checkup")){
            holder.requestTitle.setTextColor(Color.parseColor("#BD10E0"));
        }else if (mRequestList.get(position).getTitle().equalsIgnoreCase("Braces Consultation")){
            holder.requestTitle.setTextColor(Color.BLACK);
        }else if (mRequestList.get(position).getTitle().equalsIgnoreCase("Cosmetic Consultation")){
            holder.requestTitle.setTextColor(Color.parseColor("#325B04"));
        }
        holder.requestDesc.setText(mRequestList.get(position).getDesc());


        //FIXME
        // Add image support
        return v;
    }
}
