package com.example.android.surveyapp;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surveyapp.models.CovidPositive;
import com.example.android.surveyapp.models.ProximityExposure;
import com.example.android.surveyapp.models.Survey;
import com.example.android.surveyapp.survey.ConcernsActivity;
import com.example.android.surveyapp.survey.ThanksActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static  MapsActivity instance;

    private GoogleMap mMap;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private DatabaseReference databaseReference;

    public static MapsActivity getInstance(){
        return instance;
    }

    private List<CovidPositive> covidPositiveList;

    private String isNotificationEnabled = "yes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        instance = this;

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        isNotificationEnabled = languageSettings.getString("notifications", "yes");

        databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MapsActivity.this, "You must allow location access", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

        covidPositiveList = new ArrayList<>();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        databaseReference.child("CovidPositive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot positiveSnapshot:dataSnapshot.getChildren()){
                    CovidPositive covidPositive = positiveSnapshot.getValue(CovidPositive.class);
                    if(!covidPositive.getMobileNumber().equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())) {
                        LatLng latLng = new LatLng(covidPositive.getLatitude(), covidPositive.getLongitude());
                        drawCircleOnMap(latLng);
                        covidPositiveList.add(covidPositive);
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.getMessage());
            }

        });

    }

    private void drawCircleOnMap(LatLng latLng){

        mMap.addMarker(new MarkerOptions().position(latLng));

    }

    private void updateLocation(){
        buildLocationRequest();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }

    public void updateFirebaseLocation(final String value, final Location location){
        MapsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //update firebase database

                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                //mMap.addMarker(new MarkerOptions().position(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));


                databaseReference.child("CovidPositive").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.getValue() != null) {
                            // The child doesn't exist
                        }
                        else{
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("SurveyApp");
                            databaseReference1.child("CovidPositive").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        dataSnapshot.getRef().child("latitude").setValue(location.getLatitude());
                                        dataSnapshot.getRef().child("longitude").setValue(location.getLongitude());

                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d("MapsActivity", databaseError.getMessage());
                                    }
                                });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                for(CovidPositive cp: covidPositiveList){

                    Location endLocation = new Location("point B");
                    endLocation.setLatitude(cp.getLatitude());
                    endLocation.setLongitude(cp.getLongitude());

                    int dist = (int)location.distanceTo(endLocation);

                    if(dist<500) {

                        String dateTime = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                        String address = "";

                        Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


                                if (addresses != null && addresses.size() > 0) {
                                    address = addresses.get(0).getAddressLine(0);

                                }

                            } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ProximityExposure proximityExposure = new ProximityExposure(cp.getMobileNumber(), address, dist, dateTime);

                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("ProximityExposure").child(timeStamp).child(cp.getMobileNumber()).setValue(proximityExposure)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if(isNotificationEnabled.equals("yes"))
                                    createNotification(1);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


                    }
                }

            }
        });
    }


    public void createNotification(int notificationId){

        createNotificationChannel();

        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "007")
                .setSmallIcon(R.drawable.ic_app_logo)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentTitle("The app  has detected that you are near a location where someone reported testing positive for COVID-19 within the past 14 days")
                .setContentText("Find precautions and more health information in the menu")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Find precautions and more health information in the menu"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel name";
            String description = "Channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("007", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
