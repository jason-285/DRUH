package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Cinema;
import com.jason.druh.Model.CinemaAdapter;
import com.jason.druh.Model.Movie;
import com.jason.druh.Model.MovieAdapter;
import com.jason.druh.Network.ApiCinema;
import com.jason.druh.Network.ApiClient;
import com.jason.druh.Network.ApiMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CinemasActivity extends AppCompatActivity {
    List<Cinema> cinemas;
    CinemaAdapter cinemaAdapter;

    ImageButton backCBtn, logoutCBtn;
    TextView titleCTxtV;
    RecyclerView cinemasCRV;

    SharedPreferences.Editor prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinemas);

        backCBtn = findViewById(R.id.backCBtn);
        logoutCBtn = findViewById(R.id.logoutCBtn);
        titleCTxtV = findViewById(R.id.titleCTxtV);
        cinemasCRV = findViewById(R.id.cinemasCRV);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        cinemasCRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        showCinemas();

        backCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logoutCBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(CinemasActivity.this, AuthActivity.class);
                startActivity(authIntent);
            }
        });

    }

    public void showCinemas(){
        Call<List<Cinema>> call = ApiClient.getClient().create(ApiCinema.class).getCinemas();
        call.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                if (response.isSuccessful()) {
                    cinemas = response.body();
                    cinemaAdapter = new CinemaAdapter(cinemas, getApplicationContext());
                    //clickSetUp();
                    cinemasCRV.setAdapter(cinemaAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cinema>> call, Throwable t) {
                Toast.makeText(CinemasActivity.this, "404 Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}