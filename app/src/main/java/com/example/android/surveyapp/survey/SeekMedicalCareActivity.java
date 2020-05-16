package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class SeekMedicalCareActivity extends AppCompatActivity {

    private Button no, yes;
    private TextView next, previous;

    private boolean medicalCare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_medical_care);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_medical_care_no_btn);
        yes = findViewById(R.id.survey_medical_care_yes_btn);
        previous = findViewById(R.id.survey_medical_care_previous_btn);
        next = findViewById(R.id.survey_medical_care_next_btn);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                yes.setBackgroundResource(R.drawable.button_inactive);

                medicalCare = false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);

                medicalCare = true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setMedicalCare(medicalCare);
                Intent intent = new Intent(SeekMedicalCareActivity.this, PreConditionsActivity.class);
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
