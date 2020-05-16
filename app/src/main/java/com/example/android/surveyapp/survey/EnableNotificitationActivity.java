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
import android.widget.Toast;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class EnableNotificitationActivity extends AppCompatActivity {

    private Button yes, no;
    private TextView next;
    private boolean selected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_notificitation);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        no = findViewById(R.id.survey_notification_no_btn);
        yes = findViewById(R.id.survey_notification_yes_btn);
        next = findViewById(R.id.survey_notification_next_btn);

        SharedPreferences languageSettings = getSharedPreferences("LanguagePreference", MODE_PRIVATE);
        String res = languageSettings.getString("notifications", "yes");

        if(res.equals("yes")){
            yes.setBackgroundResource(R.drawable.button_active);
            no.setBackgroundResource(R.drawable.button_inactive);

            setSharedPreference("yes");

        }
        else{
            no.setBackgroundResource(R.drawable.button_active);
            yes.setBackgroundResource(R.drawable.button_inactive);

            setSharedPreference("no");
        }

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                yes.setBackgroundResource(R.drawable.button_inactive);
                selected = false;
                setSharedPreference("no");

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                no.setBackgroundResource(R.drawable.button_inactive);
                selected = true;
                setSharedPreference("yes");

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(EnableNotificitationActivity.this, MainActivity.class);
                startActivity(intent);
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
        prefEditor.putString("notifications", s);
        prefEditor.commit();
    }

}
