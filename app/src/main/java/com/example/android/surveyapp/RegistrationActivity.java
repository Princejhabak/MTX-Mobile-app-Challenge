package com.example.android.surveyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.surveyapp.models.Notification;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private final int SIGNUP_REQUEST_CODE = 10;
    private final int SIGNIN_REQUEST_CODE = 100;

    private FirebaseAuth firebaseAuth;

    private final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.PhoneBuilder().build());

    private TextView tvMobileNo, tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuth = FirebaseAuth.getInstance();

        tvMobileNo = findViewById(R.id.tv_mobile_no);
        tvSignIn = findViewById(R.id.tvSignIn);

        tvMobileNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        SIGNUP_REQUEST_CODE);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        SIGNIN_REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == SIGNUP_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {

                    final String phone_no = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                    String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    final String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                    final Notification notification = new Notification(timeStamp, "Take a Survey of COVID-19", "Thanks for signing up to recieve alerts about\nU.S. dealing with COVID-19", false);

                    final ProgressDialog pd = new ProgressDialog(this);
                    pd.setMessage("Registering User");
                    pd.setCancelable(false);
                    pd.show();

                    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");

                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            if (snapshot.getValue() == null) {
                                // The child doesn't exist
                                Toast.makeText(RegistrationActivity.this, "Phone number verified", Toast.LENGTH_SHORT).show();

                                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Notifications").child(currentDate).setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        finish();

                                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                        Toast.makeText(RegistrationActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                pd.dismiss();
                                Toast.makeText(RegistrationActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
            // Sign up failed
            else {
                if (response == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //response.getError();
                }
            }

        }

        else if(requestCode == SIGNIN_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){

                if (!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("SurveyApp");
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            if (snapshot.getValue() == null) {
                                // The child doesn't exist
                                Toast.makeText(RegistrationActivity.this, "User not registered", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(RegistrationActivity.this, "User not registered", Toast.LENGTH_SHORT).show();
                    return;

                }

            }

        }

    }


}
