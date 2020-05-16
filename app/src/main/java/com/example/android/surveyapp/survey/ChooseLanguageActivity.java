package com.example.android.surveyapp.survey;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;

public class ChooseLanguageActivity extends AppCompatActivity {

    private Button en, sp;
    private TextView entv, sptv ,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        en = findViewById(R.id.survey_language_en_btn);
        sp = findViewById(R.id.survey_language_sp_btn);
        entv = findViewById(R.id.survey_language_en_tv);
        sptv = findViewById(R.id.survey_language_sp_tv);
        next = findViewById(R.id.survey_language_next_btn);

        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        String language = languageSettings.getString("language", "en");

        if(language.equals("en")){
            en.setBackgroundResource(R.drawable.button_active);
            entv.setTextColor(getResources().getColor(R.color.green_theme));

            sp.setBackgroundResource(R.drawable.button_inactive);
            sptv.setTextColor(getResources().getColor(android.R.color.black));

            setSharedPreference("en");
        }
        else{
            sp.setBackgroundResource(R.drawable.button_active);
            sptv.setTextColor(getResources().getColor(R.color.green_theme));

            en.setBackgroundResource(R.drawable.button_inactive);
            entv.setTextColor(getResources().getColor(android.R.color.black));

            setSharedPreference("sp");
        }

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                en.setBackgroundResource(R.drawable.button_active);
                entv.setTextColor(getResources().getColor(R.color.green_theme));

                sp.setBackgroundResource(R.drawable.button_inactive);
                sptv.setTextColor(getResources().getColor(android.R.color.black));

                setSharedPreference("en");

            }
        });

        sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.setBackgroundResource(R.drawable.button_active);
                sptv.setTextColor(getResources().getColor(R.color.green_theme));

                en.setBackgroundResource(R.drawable.button_inactive);
                entv.setTextColor(getResources().getColor(android.R.color.black));

                setSharedPreference("sp");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseLanguageActivity.this, MainActivity.class));

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void setSharedPreference(String s){
        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = languageSettings.edit();
        prefEditor.putString("language", s);
        prefEditor.commit();
    }

}
