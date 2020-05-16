package com.example.android.surveyapp.survey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surveyapp.R;
import com.example.android.surveyapp.adapters.SurveyContactDetailsAdapter;
import com.example.android.surveyapp.models.Survey;
import com.example.android.surveyapp.models.SurveyContactDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactDetailsActivity extends AppCompatActivity {

    private EditText name, age, address1, address2, phone;
    private Button save, add_person;
    private TextView previous, next;
    private TextView nameView, detailView, addressView, phoneView;
    private LinearLayout linearLayout;
    private ImageButton editDetails;

    private Spinner relationSpinner, genderSpinner;

    private boolean mode_edit = false;

    private SurveyContactDetails surveyContactDetails;

    List<SurveyContactDetails> detailsList ;

    private String[] relationshipItems;
    private String[] genderItems;

    private String relationshipText = "";
    private String genderText ="";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        final Survey survey = (Survey) getIntent().getSerializableExtra("Survey");

        detailsList = new ArrayList<>();

        name = findViewById(R.id.survey_contact_details_name);
        age = findViewById(R.id.survey_contact_details_age);
        address1 = findViewById(R.id.survey_contact_details_address1);
        address2 = findViewById(R.id.survey_contact_details_address2);
        phone = findViewById(R.id.survey_contact_details_phone);

        save = findViewById(R.id.survey_contact_details_save_btn);
        add_person = findViewById(R.id.survey_contact_details_add_person_btn);
        previous = findViewById(R.id.survey_contact_details_previous_btn);
        next = findViewById(R.id.survey_contact_details_next_btn);

        linearLayout = findViewById(R.id.survey_contact_details_li_linear_layout);
        nameView = findViewById(R.id.survey_contact_details_li_name);
        detailView = findViewById(R.id.survey_contact_details_li_details);
        addressView = findViewById(R.id.survey_contact_details_li_address);
        phoneView = findViewById(R.id.survey_contact_details_li_phone);
        editDetails = findViewById(R.id.survey_contact_details_li_edit);

        relationSpinner = findViewById(R.id.survey_contact_details_relation_spinner);
        genderSpinner = findViewById(R.id.survey_contact_details_gender_spinner);

        relationshipItems = getResources().getStringArray(R.array.survey_relationship);
        genderItems = getResources().getStringArray(R.array.survey_gender);

        final ArrayAdapter relationshipAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,relationshipItems);
        relationshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationSpinner.setAdapter(relationshipAdapter);

        final ArrayAdapter genderAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genderItems);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  name_str, age_str, addr1_str, addr2_str, phone_str;

                if(TextUtils.isEmpty(name.getText())){
                    Toast.makeText(ContactDetailsActivity.this, "Please enter relation", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    name_str = name.getText().toString();


                if(TextUtils.isEmpty(age.getText())){
                    Toast.makeText(ContactDetailsActivity.this, "Please enter age", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    age_str = age.getText().toString();

                if(TextUtils.isEmpty(address1.getText())){
                    Toast.makeText(ContactDetailsActivity.this, "Please enter relation", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    addr1_str = address1.getText().toString();

                if(TextUtils.isEmpty(address2.getText())){
                    Toast.makeText(ContactDetailsActivity.this, "Please enter relation", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    addr2_str = address2.getText().toString();

                if(TextUtils.isEmpty(phone.getText())){
                    Toast.makeText(ContactDetailsActivity.this, "Please enter relation", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                    phone_str = phone.getText().toString();

                if(mode_edit){
                    surveyContactDetails.setRelationship(relationshipText);
                    surveyContactDetails.setDetailGender(genderText);

                    surveyContactDetails.setName(name_str);
                    surveyContactDetails.setDetailAge(age_str);
                    surveyContactDetails.setAddressLine1(addr1_str);
                    surveyContactDetails.setAddressLine2(addr2_str);
                    surveyContactDetails.setPhoneNo(phone_str);

                    mode_edit = false;
                }
                else {
                    surveyContactDetails = new SurveyContactDetails(relationshipText, name_str, genderText, age_str, addr1_str, addr2_str, phone_str);
                    detailsList.add(surveyContactDetails);
                }
                Toast.makeText(ContactDetailsActivity.this, "Details Saved", Toast.LENGTH_SHORT).show();

                linearLayout.setVisibility(View.VISIBLE);

                String nameViewText = surveyContactDetails.getName() + " (" + surveyContactDetails.getRelationship() + ")";
                String detailViewText = surveyContactDetails.getDetailGender() + ", " + surveyContactDetails.getDetailAge() + " Years";
                String addressViewText = surveyContactDetails.getAddressLine1() + ", " + surveyContactDetails.getAddressLine2();

                nameView.setText(nameViewText);
                detailView.setText(detailViewText);
                addressView.setText(addressViewText);
                phoneView.setText(surveyContactDetails.getPhoneNo());


            }
        });

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mode_edit = true;

                name.setText(surveyContactDetails.getName());
                age.setText(surveyContactDetails.getDetailAge());
                address1.setText(surveyContactDetails.getAddressLine1());
                address2.setText(surveyContactDetails.getAddressLine2());
                phone.setText(surveyContactDetails.getPhoneNo());

                int index1 = Arrays.binarySearch(relationshipItems, surveyContactDetails.getRelationship());
                int index2 = Arrays.binarySearch(genderItems, surveyContactDetails.getDetailGender());

                relationSpinner.setSelection(index1);
                genderSpinner.setSelection(index2);

                linearLayout.setVisibility(View.GONE);
            }
        });

        add_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name.setText("");
                age.setText("");
                address1.setText("");
                address2.setText("");
                phone.setText("");

                relationSpinner.setSelection(0);
                genderSpinner.setSelection(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                survey.setSurveyContactDetailsList(detailsList);
                Intent intent = new Intent(ContactDetailsActivity.this, CloseContactActivity.class);
                intent.putExtra("Survey", survey);
                startActivity(intent);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("Number", String.valueOf(detailsList.size()) );
                finish();
            }
        });


        relationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    relationshipText = item.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    genderText = item.toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
}
