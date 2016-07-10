package com.firstopiniondentist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.firstopiniondentist.firstopiniondentist.R;
import com.firstopiniondentist.model.Tip;

import java.util.ArrayList;

/**
 * Created by jasmeetsingh on 7/8/16.
 */

public class TipAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Tip> mTipList;
    private LayoutInflater mLayoutInflater;
    public TipAdapter(Context context, ArrayList<Tip> tipList) {
        mContext = context;
        mTipList = tipList;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView tipImage;
        public TextView tipTitle;
        public TextView tipDesc;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tipImage = (ImageView) view.findViewById(R.id.tip_item_image);
            tipTitle = (TextView) view.findViewById(R.id.tip_item_title);
            tipDesc = (TextView) view.findViewById(R.id.tip_item_desc_short);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tipTitle.getText() + "'";
        }
    }

    @Override
    public int getCount() {
        return mTipList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTipList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View v = convertView;
        ViewHolder holder;
        if(convertView == null) {
            View inflater = mLayoutInflater.inflate(R.layout.tip_item, viewGroup, false);
            holder = new ViewHolder(inflater);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();

        }
        holder.tipTitle.setText(Tip.tipsList.get(position).getTipTitle());
        holder.tipDesc.setText(Tip.tipsList.get(position).getTipDesc());
        //FIXME
        // Add image support
        return v;
    }
}
