package com.dentalcareapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dentalcareapp.adapters.RequestAdapter;
import com.dentalcareapp.model.UserDentalRequests;
import com.dentalcareapp.network.NetworkManager;
import com.dentalcareapp.network.NetworkRequest;
import com.dentalcareapp.firstopiniondentist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PastRequestActivity extends AppCompatActivity {

    private ListView mRequestListView;
    private TextView mRequestTextView;
    private RequestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_request);
        mRequestListView = (ListView)findViewById(R.id.past_request_list);
        mRequestTextView = (TextView)findViewById(R.id.no_history_request);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        adapter = new RequestAdapter(this, UserDentalRequests.dentalRequests);
        mRequestListView.setAdapter(adapter);
        NetworkManager.getInstance().getUserRequests(new NetworkRequest() {
            @Override
            public Object success(Object data) {
                JSONArray requestarray = (JSONArray)data;
                if (requestarray.length() != 0){
                    UserDentalRequests.dentalRequests.clear();
                    mRequestTextView.setVisibility(View.GONE);
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
                    mRequestTextView.setVisibility(View.VISIBLE);
                    mRequestListView.setVisibility(View.GONE);
                }
                return null;
            }


            @Override
            public Object failed(Object data) {
                return null;
            }
        });

    }

    public void populateList(){

    }
}
