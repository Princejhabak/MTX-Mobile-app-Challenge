package com.example.android.surveyapp.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.adapters.ProximityAdapter;
import com.example.android.surveyapp.adapters.ProximityDetailsAdapter;
import com.example.android.surveyapp.models.ProximityExposure;
import com.example.android.surveyapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

public class ProximityExposureDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProximityDetailsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<ProximityExposure> proximityExposureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_exposure_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        proximityExposureList = (List<ProximityExposure>) getIntent().getSerializableExtra("ExposureList");

        recyclerView  = findViewById(R.id.proximity_exposure_details_recycler_view);
        recyclerView.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProximityDetailsAdapter(this, proximityExposureList);
        recyclerView.setAdapter(adapter);

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
}
