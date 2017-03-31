package com.dentalcareapp.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.dentalcareapp.firstopiniondentist.R;
import com.dentalcareapp.model.Tip;

/**
 * Created by jasmeetsingh on 7/10/16.
 */


public class TipDetailActivity extends Activity {

    private ImageView tipImage;
    private TextView tipTitle;
    private TextView tipDesc;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.action_settings));
        setContentView(R.layout.tip_detail);
        tipImage = (ImageView)findViewById(R.id.tip_detail_image);
        tipTitle = (TextView) findViewById(R.id.tip_detail_title_text);
        tipDesc = (TextView)findViewById(R.id.tip_detail_desc_text);
        int position = getIntent().getIntExtra("tip_position",0);

        tipTitle.setText(Tip.tipsList.get(position).getTipTitle());
        tipDesc.setText(Tip.tipsList.get(position).getTipDesc());
        if(Tip.tipsList.get(position).getTipImage() != null) {
            tipImage.setImageBitmap(Tip.tipsList.get(position).getTipImage());
        }else{
            tipImage.setImageDrawable(getDrawable(R.drawable.tooth));
        }


    }
}
