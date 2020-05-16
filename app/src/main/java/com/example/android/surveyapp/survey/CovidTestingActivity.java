package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class CovidTestingActivity extends AppCompatActivity {

    private Button no, yes;
    private TextView previous,next;

    private boolean positive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_testing);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_testing_no_btn);
        yes = findViewById(R.id.survey_testing_yes_btn);
        previous = findViewById(R.id.survey_testing_previous_btn);
        next = findViewById(R.id.survey_testing_next_btn);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                yes.setBackgroundResource(R.drawable.button_inactive);
                positive = false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);
                positive = true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setTestedPositive(positive);
                Intent intent;

                if(positive)
                    intent = new Intent(CovidTestingActivity.this, ContactSharingActivity.class);
                else
                    intent = new Intent(CovidTestingActivity.this, SymptomsActivity.class);

                intent.putExtra("Survey", survey);
                startActivity(intent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
