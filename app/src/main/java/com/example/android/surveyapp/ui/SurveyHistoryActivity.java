package com.example.android.surveyapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;
import com.example.android.surveyapp.adapters.SurveyHistoryAdapter;
import com.example.android.surveyapp.models.Survey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SurveyHistoryActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth ;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private List<Survey> surveyList;

    private ListView surveysListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("SurveyApp");

        surveyList = new ArrayList<>();

        surveysListView = findViewById(R.id.survey_history_list_view);

        View emptyView = findViewById(R.id.empty_view);
        surveysListView.setEmptyView(emptyView);

        getDetailsFormDatabase();


        surveysListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Survey survey = (Survey) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(SurveyHistoryActivity.this, SurveyDetailsActivity.class);
                intent.putExtra("Survey", survey);
                startActivity(intent);
            }
        });

    }


    public void getDetailsFormDatabase(){
        mDatabaseReference.child(firebaseAuth.getCurrentUser().getPhoneNumber()).child("Surveys").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                surveyList.clear();

                for(DataSnapshot surveySnapshot:dataSnapshot.getChildren()){
                    Survey survey = surveySnapshot.getValue(Survey.class);
                    surveyList.add(survey);
                }

                SurveyHistoryAdapter adapter = new SurveyHistoryAdapter( surveyList, SurveyHistoryActivity.this);
                surveysListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
