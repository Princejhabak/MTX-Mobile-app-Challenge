package com.example.android.surveyapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.adapters.NotificationAdapter;
import com.example.android.surveyapp.adapters.ProximityAdapter;
import com.example.android.surveyapp.models.Notification;
import com.example.android.surveyapp.models.ProximityExposure;
import com.example.android.surveyapp.models.Survey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProximityExposureActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProximityAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<List<ProximityExposure>> itemList;
    private List<ProximityExposure> proximityExposureList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_exposure);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView  = findViewById(R.id.proximity_exposure_recycler_view);
        recyclerView.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");

        itemList = new ArrayList<>();
        proximityExposureList = new ArrayList<>();
        adapter = new ProximityAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        getDetailsFormDatabase();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void getDetailsFormDatabase() {

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("ProximityExposure").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot surveySnapshot:dataSnapshot.getChildren()){

                    for(DataSnapshot innerSnapshot: surveySnapshot.getChildren()){
                        ProximityExposure proximityExposure = innerSnapshot.getValue(ProximityExposure.class);
                        proximityExposureList.add(proximityExposure);
                    }
                    itemList.add(proximityExposureList);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
