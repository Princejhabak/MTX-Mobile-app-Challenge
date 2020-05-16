package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class HowRUFeelingActivity extends AppCompatActivity {

    private ImageButton sad, well;
    private TextView sadtv, welltv, next, previous;
    private String feeling = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_rufeeling);

       final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        sad = findViewById(R.id.survey_feeling_sad_btn);
        well = findViewById(R.id.survey_feeling_well_btn);
        sadtv = findViewById(R.id.survey_feeling_sad_tv);
        welltv = findViewById(R.id.survey_feeling_well_tv);
        previous = findViewById(R.id.survey_feeling_previous_btn);
        next = findViewById(R.id.survey_feeling_next_btn);

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sad.setBackgroundResource(R.drawable.button_active);
                sadtv.setTextColor(getResources().getColor(R.color.green_theme));

                well.setBackgroundResource(R.drawable.button_inactive);
                welltv.setTextColor(getResources().getColor(android.R.color.black));

                feeling = "Sick";

            }
        });

        well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                well.setBackgroundResource(R.drawable.button_active);
                welltv.setTextColor(getResources().getColor(R.color.green_theme));

                sad.setBackgroundResource(R.drawable.button_inactive);
                sadtv.setTextColor(getResources().getColor(android.R.color.black));

                feeling = "Well";

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setFeeling(feeling);

                Intent intent = new Intent(HowRUFeelingActivity.this, CovidTestingActivity.class);
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
