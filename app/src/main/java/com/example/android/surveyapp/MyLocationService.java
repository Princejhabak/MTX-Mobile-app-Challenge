package com.example.android.surveyapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver {

    public static final String ACTION_PROCESS_UPDATES = "com.example.android.surveyapp.UPDATE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent!=null){
            final String action = intent.getAction();
            if (ACTION_PROCESS_UPDATES.equals(action)){

                LocationResult result = LocationResult.extractResult(intent);
                if(result!=null){
                    Location location = result.getLastLocation();
                    String location_string = new StringBuilder("" + location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();
                    try{
                        MapsActivity.getInstance().updateFirebaseLocation(location_string, location);
                    }catch (Exception ex){
                        Toast.makeText(context, "Inside catch", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        }


    }

}
