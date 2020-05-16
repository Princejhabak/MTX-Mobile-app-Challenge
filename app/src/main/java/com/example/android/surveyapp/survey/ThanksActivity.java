package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;

public class ThanksActivity extends AppCompatActivity {

    TextView back, link1, link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        back = findViewById(R.id.survey_thanks_back);
        link1 = findViewById(R.id.survey_thanks_tv1);
        link2 = findViewById(R.id.survey_thanks_tv2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThanksActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hhs.gov/"));
                startActivity(browserIntent);
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.who.int/health-topics/coronavirus#tab=tab_1"));
                startActivity(browserIntent);
            }
        });


    }
}
