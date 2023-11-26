package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Movie;

public class MovieActivity extends AppCompatActivity {
    Movie movie;
    String number, website;

    ImageButton backDBtn, logoutDBtn;
    ImageView posterDImgV;
    TextView titleDTxtV, categoryDTxtV, timeDTxtV, agesDTxtV, languagesDTxtV, descriptionDTxtV;
    Button messageDBtn, callDBtn, pageDBtn;

    SharedPreferences.Editor prefs;
    Bundle movieBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backDBtn = findViewById(R.id.backDBtn);
        logoutDBtn = findViewById(R.id.logoutDBtn);
        posterDImgV = findViewById(R.id.posterDImgV);
        titleDTxtV = findViewById(R.id.titleDTxtV);
        categoryDTxtV = findViewById(R.id.categoryDTxtV);
        timeDTxtV = findViewById(R.id.timeDTxtV);
        agesDTxtV = findViewById(R.id.agesDTxtV);
        languagesDTxtV = findViewById(R.id.languagesDTxtV);
        descriptionDTxtV = findViewById(R.id.descriptionDTxtV);
        messageDBtn = findViewById(R.id.messageDBtn);
        callDBtn = findViewById(R.id.callDBtn);
        pageDBtn = findViewById(R.id.pageDBtn);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();

        autofill();

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

        messageDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number,null,"Hi, I wanna buy tickets for: "+movie.getTitle()+"!!!", null, null);

                Toast.makeText(getApplicationContext(),"Message sended succesfully",Toast.LENGTH_SHORT).show();
            }
        });

        callDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int phone = Integer.parseInt(number);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+phone));
                startActivity(callIntent);
            }
        });

        pageDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getWebsite()));
                startActivity(intent);
            }
        });
    }

    public void autofill(){
        movieBundle = this.getIntent().getExtras();
        movie = movieBundle.getParcelable("Movie");
        Glide.with(this).load(movie.getHeader()).into(posterDImgV);
        titleDTxtV.setText(movie.getTitle());
        categoryDTxtV.setText(movie.getCategory());
        timeDTxtV.setText(movie.getTime());
        agesDTxtV.setText(movie.getAges());
        languagesDTxtV.setText(movie.getLanguages());
        descriptionDTxtV.setText(movie.getDescription());
        number = movie.getNumber();
        website = movie.getWebsite();
    }
}