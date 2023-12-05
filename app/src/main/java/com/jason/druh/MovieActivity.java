//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Movie;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class MovieActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    Movie movie;
    String number, website;

    ImageButton backDBtn, searchDBtn, logoutDBtn, messageDBtn, callDBtn, pageDBtn;
    ImageView posterDImgV;
    TextView titleDTxtV, categoryDTxtV, timeDTxtV, agesDTxtV, languagesDTxtV, descriptionDTxtV;

    SharedPreferences.Editor prefs;
    Bundle movieBundle;

    //----------------------------------------------------------------------------------------------

    /*ONCREATE METHOD*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //------------------------------------------------------------------------------------------

        /*VARS INIT*/

        backDBtn = findViewById(R.id.backDBtn);
        searchDBtn = findViewById(R.id.searchDBtn);
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

        //------------------------------------------------------------------------------------------

        /*PRE-CHARGE METHODS*/

        prefs = getSharedPreferences(getString(R.string.prefs_file),
                Context.MODE_PRIVATE).edit();

        autofill();

        //------------------------------------------------------------------------------------------

        /*LISTENER METHODS*/

        backDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        }); // END LISTENER

        searchDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MovieActivity.this,
                        SearchActivity.class);
                startActivity(searchIntent);
            }
        }); // END LISTENER

        logoutDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(MovieActivity.this, AuthActivity.class);
                startActivity(authIntent);

            }
        }); // END LISTENER

        messageDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(number,null,"Hi, I wanna buy tickets for: "
                        +movie.getTitle()+"!!!", null, null);

                Toast.makeText(getApplicationContext(),"Message sended succesfully",
                        Toast.LENGTH_SHORT).show();
            }
        }); // END LISTENER

        callDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int phone = Integer.parseInt(number);
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+phone));
                startActivity(callIntent);
            }
        }); // END LISTENER

        pageDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getWebsite()));
                startActivity(intent);
            }
        }); // END LISTENER

        //------------------------------------------------------------------------------------------

    } // END ONCREATE

    //----------------------------------------------------------------------------------------------

    /*HELPERS METHODS*/

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
    } // END AUTOFILL

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------