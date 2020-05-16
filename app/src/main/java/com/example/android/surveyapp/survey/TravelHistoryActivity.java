package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class TravelHistoryActivity extends AppCompatActivity {

    private ImageButton yes, no;
    private TextView yestv, notv, next, previous, heading;
    private EditText places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);

        final Survey survey = (Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_th_no_btn);
        yes = findViewById(R.id.survey_th_yes_btn);
        notv = findViewById(R.id.survey_th_no_tv);
        yestv = findViewById(R.id.survey_th_yes_tv);
        previous = findViewById(R.id.survey_th_previous_btn);
        next = findViewById(R.id.survey_th_next_btn);
        heading = findViewById(R.id.survey_th_tv);
        places = findViewById(R.id.survey_th_et);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                notv.setTextColor(getResources().getColor(R.color.green_theme));

                yes.setBackgroundResource(R.drawable.button_inactive);
                yestv.setTextColor(getResources().getColor(android.R.color.black));

                heading.setVisibility(View.GONE);
                places.setVisibility(View.GONE);

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                yestv.setTextColor(getResources().getColor(R.color.green_theme));

                no.setBackgroundResource(R.drawable.button_inactive);
                notv.setTextColor(getResources().getColor(android.R.color.black));

                heading.setVisibility(View.VISIBLE);
                places.setVisibility(View.VISIBLE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = places.getText().toString();

                survey.setTravelHistory(str);

                Intent intent = new Intent(TravelHistoryActivity.this, HowRUFeelingActivity.class);
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
