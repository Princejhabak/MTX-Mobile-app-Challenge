package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class AgeSelectorActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView next, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_selector);

        seekBar = findViewById(R.id.survey_age_seekbar);
        previous = findViewById(R.id.survey_age_previous_btn);
        next = findViewById(R.id.survey_age_next_btn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int age_int = seekBar.getProgress();
                String age = "";
                if(age_int<18)
                    age = "Under 18";
                else if(age_int>=18&&age_int<=30)
                    age = "18-30";
                else if(age_int>30&&age_int<=50)
                    age = "31-50";
                else if(age_int>51&&age_int<=64)
                    age = "51-64";
                else
                    age = "65+";

                Survey survey = new Survey();
                survey.setEnableNotification(false);
                survey.setAge(age);

                Intent intent = new Intent(AgeSelectorActivity.this, ResidencyCountryActivity.class);
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
