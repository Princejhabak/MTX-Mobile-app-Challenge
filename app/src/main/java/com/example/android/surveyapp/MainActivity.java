package com.example.android.surveyapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.android.surveyapp.models.Notification;
import com.example.android.surveyapp.survey.AgeSelectorActivity;
import com.example.android.surveyapp.survey.ChooseLanguageActivity;
import com.example.android.surveyapp.survey.EnableNotificitationActivity;
import com.example.android.surveyapp.ui.NotificationsActivity;
import com.example.android.surveyapp.ui.ProximityExposureActivity;
import com.example.android.surveyapp.ui.SurveyHistoryActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private final int REQUEST_PERMISSION_CODE = 007;

//    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth firebaseAuth ;

//    private DrawerLayout drawerLayout ;
    private ActionBarDrawerToggle mToggle;

    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private DatabaseReference databaseReference;

    private Button startSurveyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_app_logo);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");

        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);

        if(firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            generateNotification();

            if(!languageSettings.contains("notifications")) {
                Intent intent1 = new Intent(this, EnableNotificitationActivity.class);
                startActivity(intent1);
            }

            if(!languageSettings.contains("language")) {
                Intent intent = new Intent(this, ChooseLanguageActivity.class);
                startActivity(intent);

            }

        }

        if (!checkPermissionFromDevice()) {
            requestPermission();
        }
        checkInternetConnection();
        checkLocationEnabled();

        startSurveyButton = findViewById(R.id.survey_instructions_start_btn);

        if(languageSettings.contains("taken_survey")) {
            String str ="Retake Survey";
            startSurveyButton.setText(str);
        }

        startSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, AgeSelectorActivity.class));

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermissionFromDevice()) {
                    requestPermission();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent);
                }
            }
        });


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void generateNotification() {

        final String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        final String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Notifications").child(currentDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.getValue() == null) {
                    // The child doesn't exist

                    Notification notification = new Notification(timeStamp, "Take a Survey of COVID-19", "Thanks for signing up to recieve alerts about\nU.S. dealing with COVID-19", false);
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Notifications").child(currentDate).setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           Log.e("MainActivity", "Failed to create notification");
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_logout){
            FirebaseAuth.getInstance().signOut();

            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_take_survey) {
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (id == R.id.nav_survey_history) {
            startActivity(new Intent(this, SurveyHistoryActivity.class));
        }
        else if (id == R.id.nav_proximity_exposure) {
            startActivity(new Intent(this, ProximityExposureActivity.class));
        }
        else if (id == R.id.nav_exposure_setting) {
            startActivity(new Intent(this, EnableNotificitationActivity.class));
        }
        else if (id == R.id.nav_language_setting) {
            startActivity(new Intent(this, ChooseLanguageActivity.class));
        }
        else if (id == R.id.nav_notifications) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_guidance) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cdc.gov/coronavirus/2019-ncov/index.html"));
            startActivity(browserIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION_CODE);
    }

    private boolean checkPermissionFromDevice() {
        int location_result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return location_result == PackageManager.PERMISSION_GRANTED;
    }

    private void checkInternetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if(nInfo != null && nInfo.isConnected()){

        }
        else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this) ;
            a_builder.setMessage("Please enable Internet Connection.")
                    .setCancelable(false)
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            Intent in = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS  );
                            startActivity(in);

                        }
                    } )
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("No Internet Connection");
            alert.show();
        }
    }

    private void checkLocationEnabled(){
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage("Please turn on gps")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Open location setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })

                    .show();
        }
    }


}
