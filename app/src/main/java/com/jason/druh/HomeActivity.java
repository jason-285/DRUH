package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.prefs.Preferences;

public class HomeActivity extends AppCompatActivity {
    String email;

    TextView emailTxtV;
    Button logoutBtn;
    Bundle bundle;

    SharedPreferences.Editor prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        emailTxtV = findViewById(R.id.emailTxtV);
        logoutBtn = findViewById(R.id.logoutBtn);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        bundle = new Bundle(this.getIntent().getExtras());
        email = bundle.get("email").toString();

        emailTxtV.setText(email);


        autoAuth();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(authIntent);
            }
        });
    }

    private void autoAuth(){
        prefs.putString("email", email);
        prefs.apply();
    }
}