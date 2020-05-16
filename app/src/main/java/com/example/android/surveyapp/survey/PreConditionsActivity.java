package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

import java.util.ArrayList;
import java.util.List;

public class PreConditionsActivity extends AppCompatActivity {

    private TextView a, b, c, d, e, f, g, previous, next;
    private boolean is_a = false, is_b = false, is_c = false, is_d = false, is_e = false, is_f = false, is_g = false;

    private List<String> preConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_conditions);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");
        preConditions = new ArrayList<>();

        a = findViewById(R.id.survey_pre_cond_a);
        b = findViewById(R.id.survey_pre_cond_b);
        c = findViewById(R.id.survey_pre_cond_c);
        d = findViewById(R.id.survey_pre_cond_d);
        e = findViewById(R.id.survey_pre_cond_e);
        f = findViewById(R.id.survey_pre_cond_f);
        g = findViewById(R.id.survey_pre_cond_g);
        previous = findViewById(R.id.survey_pre_cond_previous_btn);
        next = findViewById(R.id.survey_pre_cond_next_btn);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = a.getText().toString();

                if(is_a){
                    a.setBackgroundResource(R.drawable.button_inactive);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preConditions.remove(text);
                    is_a = false;

                }
                else{
                    a.setBackgroundResource(R.drawable.button_active);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
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
                    preConditions.remove(text);
                    is_b = false;

                }
                else{
                    b.setBackgroundResource(R.drawable.button_active);
                    b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
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
                    preConditions.remove(text);
                    is_c = false;

                }
                else{
                    c.setBackgroundResource(R.drawable.button_active);
                    c.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
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
                    preConditions.remove(text);
                    is_d = false;

                }
                else{
                    d.setBackgroundResource(R.drawable.button_active);
                    d.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
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
                    preConditions.remove(text);
                    is_e = false;

                }
                else{
                    e.setBackgroundResource(R.drawable.button_active);
                    e.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
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
                    preConditions.remove(text);
                    is_f = false;

                }
                else{
                    f.setBackgroundResource(R.drawable.button_active);
                    f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
                    is_f = true;

                }
            }

        });

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = g.getText().toString();

                if(is_g){
                    g.setBackgroundResource(R.drawable.button_inactive);
                    g.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    preConditions.remove(text);
                    is_g = false;

                }
                else{
                    g.setBackgroundResource(R.drawable.button_active);
                    g.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    preConditions.add(text);
                    is_g = true;

                }
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setPreConditionsList(preConditions);
                Intent intent = new Intent(PreConditionsActivity.this, CovidPreventionActivity.class);
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
