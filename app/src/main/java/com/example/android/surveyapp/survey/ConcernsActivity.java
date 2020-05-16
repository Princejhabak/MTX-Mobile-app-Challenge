package com.example.android.surveyapp.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;
import com.example.android.surveyapp.RegistrationActivity;
import com.example.android.surveyapp.models.CovidPositive;
import com.example.android.surveyapp.models.Survey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ConcernsActivity extends AppCompatActivity {

    private TextView a, b, c, d, e, f, previous, next;
    private boolean is_a = false, is_b = false, is_c = false, is_d = false, is_e = false, is_f = false;

    private List<String> concernsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concerns);

        final Survey survey = (com.example.android.surveyapp.models.Survey) getIntent().getSerializableExtra("Survey");
        concernsList = new ArrayList<>();

        a = findViewById(R.id.survey_concerns_a);
        b = findViewById(R.id.survey_concerns_b);
        c = findViewById(R.id.survey_concerns_c);
        d = findViewById(R.id.survey_concerns_d);
        e = findViewById(R.id.survey_concerns_e);
        f = findViewById(R.id.survey_concerns_f);
        previous = findViewById(R.id.survey_concerns_previous);
        next = findViewById(R.id.survey_concerns_next);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = a.getText().toString();

                if(is_a){
                    a.setBackgroundResource(R.drawable.button_inactive);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    concernsList.remove(text);
                    is_a = false;

                }
                else{
                    a.setBackgroundResource(R.drawable.button_active);
                    a.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
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
                    concernsList.remove(text);
                    is_b = false;

                }
                else{
                    b.setBackgroundResource(R.drawable.button_active);
                    b.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
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
                    concernsList.remove(text);
                    is_c = false;

                }
                else{
                    c.setBackgroundResource(R.drawable.button_active);
                    c.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
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
                    concernsList.remove(text);
                    is_d = false;

                }
                else{
                    d.setBackgroundResource(R.drawable.button_active);
                    d.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
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
                    concernsList.remove(text);
                    is_e = false;

                }
                else{
                    e.setBackgroundResource(R.drawable.button_active);
                    e.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
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
                    concernsList.remove(text);
                    is_f = false;

                }
                else{
                    f.setBackgroundResource(R.drawable.button_active);
                    f.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_selected, 0);
                    concernsList.add(text);
                    is_f = true;

                }
            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog pd = new ProgressDialog(ConcernsActivity.this);
                pd.setMessage("Collecting survey data");
                pd.setCancelable(false);
                pd.show();

                String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                survey.setConcernsList(concernsList);
                survey.setDateTime(timeStamp);

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Surveys").child(survey.getDateTime()).setValue(survey).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        setSharedPreference("yes");

                        if(survey.isTestedPositive()){
                            CovidPositive covidPositive = new CovidPositive();
                            covidPositive.setMobileNumber(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                            databaseReference.child("CovidPositive").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).setValue(covidPositive).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    pd.dismiss();
                                    finish();

                                    Intent intent = new Intent(ConcernsActivity.this, ThanksActivity.class);
                                    //intent.putExtra("Survey", survey);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ConcernsActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{

                            databaseReference.child("CovidPositive").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).removeValue();

                            pd.dismiss();
                            finish();

                            Intent intent = new Intent(ConcernsActivity.this, ThanksActivity.class);
                            //intent.putExtra("Survey", survey);
                            startActivity(intent);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(ConcernsActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setSharedPreference(String s){

        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = languageSettings.edit();
        prefEditor.putString("taken_survey", s);
        prefEditor.commit();
    }
}
