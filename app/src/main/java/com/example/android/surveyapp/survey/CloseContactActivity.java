package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class CloseContactActivity extends AppCompatActivity {

    private Button no, yes, unsure;
    private TextView next, previous;

    private String closeContact = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_contact);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_cc_no_btn);
        yes = findViewById(R.id.survey_cc_yes_btn);
        unsure = findViewById(R.id.survey_cc_unsure_btn);
        previous = findViewById(R.id.survey_cc_previous_btn);
        next = findViewById(R.id.survey_cc_next_btn);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                yes.setBackgroundResource(R.drawable.button_inactive);
                unsure.setBackgroundResource(R.drawable.button_inactive);
                closeContact = "No";
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);
                unsure.setBackgroundResource(R.drawable.button_inactive);
                closeContact = "Yes";
            }
        });

        unsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unsure.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);
                yes.setBackgroundResource(R.drawable.button_inactive);
                closeContact = "Unsure";
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setCloseContact(closeContact);
                Intent intent = new Intent(CloseContactActivity.this, SeekMedicalCareActivity.class);
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
