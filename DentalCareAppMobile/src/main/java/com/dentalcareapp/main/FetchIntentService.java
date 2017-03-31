package com.dentalcareapp.main;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.dentalcareapp.model.UserProfile;
import com.dentalcareapp.util.GeneralUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by jasmeetsingh on 8/11/16.
 */

public class FetchIntentService extends IntentService {

    public FetchIntentService(){
        super("");
    }

    protected ResultReceiver mReceiver;
    private static final String TAG = FetchIntentService.class.getName();

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(GeneralUtil.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(
                GeneralUtil.LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(GeneralUtil.RECEIVER);


        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = "Service Not available";
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = "Invalid Lat Lng";
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }


        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = "Address not found";
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(GeneralUtil.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            UserProfile.getInstance().getLocation().setZipCode(address.getPostalCode());
            UserProfile.getInstance().getLocation().setState(address.getAdminArea());
            UserProfile.getInstance().getLocation().setCity(address.getLocality());
            UserProfile.getInstance().getLocation().setCountry(address.getCountryName());


            ArrayList<String> addressFragments = new ArrayList<String>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
           // Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(GeneralUtil.SUCCESS_RESULT,
                    TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
        }

    }

}
