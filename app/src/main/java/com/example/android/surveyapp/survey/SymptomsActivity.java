package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

public class SymptomsActivity extends AppCompatActivity {

    private ImageButton fever,cough, breadth;
    private TextView fevertv, coughtv, breadthtv, next, previous;

    private boolean isFever =false, isCough = false, isBreadth = false;

    private List<String> symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        symptoms = new ArrayList<>();

        fever = findViewById(R.id.survey_symptoms_fever_btn);
        cough = findViewById(R.id.survey_symptoms_cough_btn);
        breadth = findViewById(R.id.survey_symptoms_breadth_btn);
        fevertv = findViewById(R.id.survey_symptoms_fever_tv);
        coughtv = findViewById(R.id.survey_symptoms_cough_tv);
        breadthtv = findViewById(R.id.survey_symptoms_breadth_tv);
        previous = findViewById(R.id.survey_symptoms_previous_btn);
        next = findViewById(R.id.survey_symptoms_next_btn);

        fever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFever){
                    fever.setBackgroundResource(R.drawable.button_inactive);
                    fevertv.setTextColor(getResources().getColor(android.R.color.black));

                    symptoms.remove("Fever");
                    isFever = false;
                }
                else{
                    fever.setBackgroundResource(R.drawable.button_active);
                    fevertv.setTextColor(getResources().getColor(R.color.green_theme));

                    symptoms.add("Fever");
                    isFever = true;
                }


            }
        });

        cough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isCough){
                    cough.setBackgroundResource(R.drawable.button_inactive);
                    coughtv.setTextColor(getResources().getColor(android.R.color.black));

                    symptoms.remove("Cough");
                    isCough = false;
                }
                else{
                    cough.setBackgroundResource(R.drawable.button_active);
                    coughtv.setTextColor(getResources().getColor(R.color.green_theme));

                    symptoms.add("Cough");
                    isCough = true;
                }

            }
        });

        breadth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isBreadth){
                    breadth.setBackgroundResource(R.drawable.button_inactive);
                    breadthtv.setTextColor(getResources().getColor(android.R.color.black));

                    symptoms.remove("Shortness of breadth");
                    isBreadth = false;
                }
                else{
                    breadth.setBackgroundResource(R.drawable.button_active);
                    breadthtv.setTextColor(getResources().getColor(R.color.green_theme));

                    symptoms.add("Shortness of breadth");
                    isBreadth = true;
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setSymptoms(symptoms);
                Intent intent = new Intent(SymptomsActivity.this, CloseContactActivity.class);
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
