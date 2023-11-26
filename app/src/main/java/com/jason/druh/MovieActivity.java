package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;

public class MovieActivity extends AppCompatActivity {
    ImageButton backDBtn, logoutDBtn;
    ImageView posterDImgV, cinemaDImgV;
    TextView titleDTxtV, categoryDTxtV, timeDTxtV, agesDTxtV, languagesDTxtV, descriptionDTxtV, cinemaDTxtV, schedulesTitleDTxtV, schedulesDtxtV, locationTitleDTxtV;
    MapView locationDMapV;
    Button messageDBtn, callDBtn, pageDBtn;

    SharedPreferences.Editor prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backDBtn = findViewById(R.id.backDBtn);
        logoutDBtn = findViewById(R.id.logoutDBtn);
        posterDImgV = findViewById(R.id.posterDImgV);
        cinemaDImgV = findViewById(R.id.cinemaDImgV);
        titleDTxtV = findViewById(R.id.titleDTxtV);
        categoryDTxtV = findViewById(R.id.categoryDTxtV);
        timeDTxtV = findViewById(R.id.timeDTxtV);
        agesDTxtV = findViewById(R.id.agesDTxtV);
        languagesDTxtV = findViewById(R.id.languagesDTxtV);
        descriptionDTxtV = findViewById(R.id.descriptionDTxtV);
        cinemaDTxtV = findViewById(R.id.cinemaDTxtV);
        schedulesTitleDTxtV = findViewById(R.id.schedulesTitleDTxtV);
        schedulesDtxtV = findViewById(R.id.schedulesDtxtV);
        locationTitleDTxtV = findViewById(R.id.locationTitleDTxtV);
        locationDMapV = findViewById(R.id.locationDMapV);
        messageDBtn = findViewById(R.id.messageDBtn);
        callDBtn = findViewById(R.id.callDBtn);
        pageDBtn = findViewById(R.id.pageDBtn);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();

        backDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logoutDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(MovieActivity.this, AuthActivity.class);
                startActivity(authIntent);
            }
        });
    }
}