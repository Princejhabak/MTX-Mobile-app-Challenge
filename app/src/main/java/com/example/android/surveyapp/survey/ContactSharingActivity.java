package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class ContactSharingActivity extends AppCompatActivity {

    private Button no, yes;
    private TextView next, previous;

    private boolean selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_sharing);

        final Survey survey = (Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_contact_sharing_no_btn);
        yes = findViewById(R.id.survey_contact_sharing_yes_btn);
        previous = findViewById(R.id.survey_contact_sharing_previous_btn);
        next = findViewById(R.id.survey_contact_sharing_next_btn);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                yes.setBackgroundResource(R.drawable.button_inactive);
                selected = false;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);
                selected = true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setShareContacts(selected);

                if(selected){
                    Intent intent = new Intent(ContactSharingActivity.this, ContactDetailsActivity.class);
                    intent.putExtra("Survey", survey);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ContactSharingActivity.this, CloseContactActivity.class);
                    intent.putExtra("Survey", survey);
                    startActivity(intent);
                }

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
