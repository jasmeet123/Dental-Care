package com.dentalcareapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dentalcareapp.firstopiniondentist.R;

public class AboutActivity extends AppCompatActivity {

    String brief = "DentalCareApp offers free online dental care services enabling our members to report dental problems, get an expert dental opinion and engage with healthcare professionals to obtain dental and health services ";

    String about_text =
            "* Weekly professional tips for disease prevention.\n" +
            "\n" +
            "* Free Online consultations with rapid response time from an experienced dentist.\n" +

                    "\n" +
            "* Experienced licensed dentists providing you quality advice.\n" +
                    "\n" +
            "* Convenience - no need to make an appointment or miss work. Just download our app, sign-up and we'll get you help in minutes (during business hours).\n";

    private TextView briefText;
    private TextView benefitText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        briefText = (TextView)findViewById(R.id.brief_about);
        briefText.setText(brief);
        benefitText = (TextView)findViewById(R.id.benefit_desc);
        benefitText.setText(about_text);

    }
}
