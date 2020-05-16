package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class GenderActivity extends AppCompatActivity {

    private ImageButton female, male;
    private Button skip;
    private TextView ftv, mtv, next, previous;

    private String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        final Survey survey = (Survey) getIntent().getSerializableExtra("Survey");

        female = findViewById(R.id.survey_gender_f_btn);
        male = findViewById(R.id.survey_gender_m_btn);
        skip = findViewById(R.id.survey_gender_skip_btn);
        ftv = findViewById(R.id.survey_gender_f_tv);
        mtv = findViewById(R.id.survey_gender_m_tv);
        previous = findViewById(R.id.survey_gender_previous_btn);
        next = findViewById(R.id.survey_gender_next_btn);

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                female.setBackgroundResource(R.drawable.button_active);
                ftv.setTextColor(getResources().getColor(R.color.green_theme));

                skip.setBackgroundResource(R.drawable.button_inactive);

                male.setBackgroundResource(R.drawable.button_inactive);
                mtv.setTextColor(getResources().getColor(android.R.color.black));

                gender = "female";

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                male.setBackgroundResource(R.drawable.button_active);
                mtv.setTextColor(getResources().getColor(R.color.green_theme));

                skip.setBackgroundResource(R.drawable.button_inactive);

                female.setBackgroundResource(R.drawable.button_inactive);
                ftv.setTextColor(getResources().getColor(android.R.color.black));

                gender = "Male";

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip.setBackgroundResource(R.drawable.button_active);

                female.setBackgroundResource(R.drawable.button_inactive);
                ftv.setTextColor(getResources().getColor(android.R.color.black));

                male.setBackgroundResource(R.drawable.button_inactive);
                mtv.setTextColor(getResources().getColor(android.R.color.black));

                gender = "Other";

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setGender(gender);
                Intent intent = new Intent(GenderActivity.this, TravelHistoryActivity.class);
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
