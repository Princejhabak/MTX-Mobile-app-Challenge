package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.models.Survey;

public class ResidencyCountryActivity extends AppCompatActivity {

    private ImageButton yes, no;
    private TextView yestv, notv, next, previous, heading;

    private LinearLayout spinnerLayout;

    private Spinner spinner;
    private String spinnerText = "";
    private String[] spinnerItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residency_country);

        final Survey survey = (Survey) getIntent().getSerializableExtra("Survey");

        no = findViewById(R.id.survey_residency_no_btn);
        yes = findViewById(R.id.survey_residency_yes_btn);
        notv = findViewById(R.id.survey_residency_no_tv);
        yestv = findViewById(R.id.survey_residency_yes_tv);
        previous = findViewById(R.id.survey_residency_previous_btn);
        next = findViewById(R.id.survey_residency_next_btn);
        heading = findViewById(R.id.survey_residency_tv);

        spinnerLayout = findViewById(R.id.survey_residency_ll_spinner);
        spinner = findViewById(R.id.survey_residency_spinner);

        spinnerItems = getResources().getStringArray(R.array.countries);

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no.setBackgroundResource(R.drawable.button_active);
                notv.setTextColor(getResources().getColor(R.color.green_theme));

                yes.setBackgroundResource(R.drawable.button_inactive);
                yestv.setTextColor(getResources().getColor(android.R.color.black));

                heading.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.VISIBLE);

            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.button_active);
                yestv.setTextColor(getResources().getColor(R.color.green_theme));

                no.setBackgroundResource(R.drawable.button_inactive);
                notv.setTextColor(getResources().getColor(android.R.color.black));

                heading.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.GONE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinnerText.equals(""))
                    spinnerText = "USA";

                survey.setCountry(spinnerText);
                Intent intent = new Intent(ResidencyCountryActivity.this, GenderActivity.class);
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    spinnerText = item.toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}
