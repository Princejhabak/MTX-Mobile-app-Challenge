package com.example.android.surveyapp.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SurveyDetailsActivity extends AppCompatActivity {

    TextView dateTime;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11;
    TextView h41,t41;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        dateTime = findViewById(R.id.survey_details_date_time);

        t1 = findViewById(R.id.survey_details_t1);
        t2 = findViewById(R.id.survey_details_t2);
        t3 = findViewById(R.id.survey_details_t3);
        t4 = findViewById(R.id.survey_details_t4);
        t5 = findViewById(R.id.survey_details_t5);
        t6 = findViewById(R.id.survey_details_t6);
        t7 = findViewById(R.id.survey_details_t7);
        t8 = findViewById(R.id.survey_details_t8);
        t9 = findViewById(R.id.survey_details_t9);
        t10 = findViewById(R.id.survey_details_t10);
        t11 = findViewById(R.id.survey_details_t11);

        h41 = findViewById(R.id.survey_details_h41);
        t41 = findViewById(R.id.survey_details_t41);

        Long unixSeconds = Long.parseLong(survey.getDateTime());
        Date date = new java.util.Date(unixSeconds*1000L);

        String fd = formatDate(date);
        String ft = formatTime(date);
        String dt = fd + " - " + ft;
        dateTime.setText(dt);

        t1.setText(survey.getAge());
        t2.setText(survey.getCountry());
        t3.setText(survey.getGender());

        if(survey.getTravelHistory().equals("")){
            t4.setText("No");
            t41.setVisibility(View.GONE);
            h41.setVisibility(View.GONE);
        }
        else{
            t4.setText("Yes");
            t41.setText(survey.getTravelHistory());
        }

        t5.setText(survey.getCloseContact());
        t6.setText(survey.getFeeling());


        try {
            String symptoms = "";
            for (String s : survey.getSymptoms())
                symptoms = symptoms + s + "\n";

            t7.setText(symptoms);
        }catch (Exception e){
            t7.setText("");
        }

        if(survey.isMedicalCare())
            t8.setText("Yes");
        else
            t8.setText("No");

        String preConditions = "";

        try {
            for (String s : survey.getPreConditionsList())
                preConditions = preConditions + s + "\n";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            t9.setText(preConditions);
        }


        String preventionSteps = "";
        try {
            for(String s: survey.getPreventionStepsList())
                preventionSteps = preventionSteps + s +"\n";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            t10.setText(preventionSteps);
        }

        String concernsList = "";
        try{
            for(String s: survey.getConcernsList())
                concernsList = concernsList + s +"\n";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            t10.setText(concernsList);
        }

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

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
