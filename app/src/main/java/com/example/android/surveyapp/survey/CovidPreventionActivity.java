package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

public class CovidPreventionActivity extends AppCompatActivity {

    private TextView a, b, c, d, e, f, previous, next;
    private boolean is_a = false, is_b = false, is_c = false, is_d = false, is_e = false, is_f = false;

    private List<String> preventionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_prevention);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");
        preventionList = new ArrayList<>();

        a = findViewById(R.id.survey_prevention_a);
        b = findViewById(R.id.survey_prevention_b);
        c = findViewById(R.id.survey_prevention_c);
        d = findViewById(R.id.survey_prevention_d);
        e = findViewById(R.id.survey_prevention_e);
        f = findViewById(R.id.survey_prevention_f);
        previous = findViewById(R.id.survey_prevention_previous);
        next = findViewById(R.id.survey_prevention_next);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = a.getText().toString();

                if(is_a){
                    a.setBackgroundResource(R.drawable.button_inactive);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_a = false;

                }
                else{
                    a.setBackgroundResource(R.drawable.button_active);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_a = true;

                }
            }

        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = b.getText().toString();

                if(is_b){
                    b.setBackgroundResource(R.drawable.button_inactive);
                    b.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_b = false;

                }
                else{
                    b.setBackgroundResource(R.drawable.button_active);
                    b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_b = true;

                }
            }

        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = c.getText().toString();

                if(is_c){
                    c.setBackgroundResource(R.drawable.button_inactive);
                    c.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_c = false;

                }
                else{
                    c.setBackgroundResource(R.drawable.button_active);
                    c.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_c = true;

                }
            }

        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = d.getText().toString();

                if(is_d){
                    d.setBackgroundResource(R.drawable.button_inactive);
                    d.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_d = false;

                }
                else{
                    d.setBackgroundResource(R.drawable.button_active);
                    d.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_d = true;

                }
            }

        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = e.getText().toString();

                if(is_e){
                    e.setBackgroundResource(R.drawable.button_inactive);
                    e.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_e = false;

                }
                else{
                    e.setBackgroundResource(R.drawable.button_active);
                    e.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_e = true;

                }
            }

        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = f.getText().toString();

                if(is_f){
                    f.setBackgroundResource(R.drawable.button_inactive);
                    f.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preventionList.remove(text);
                    is_f = false;

                }
                else{
                    f.setBackgroundResource(R.drawable.button_active);
                    f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preventionList.add(text);
                    is_f = true;

                }
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setPreventionStepsList(preventionList);
                Intent intent = new Intent(CovidPreventionActivity.this, ConcernsActivity.class);
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
