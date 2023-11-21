package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Movie;
import com.jason.druh.Model.MovieAdapter;
import com.jason.druh.Network.ApiClient;
import com.jason.druh.Network.ApiMovie;

import java.util.List;
import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    String email;
    List<Movie> movies;
    MovieAdapter movieAdapter;

    RecyclerView moviesRV;

    Bundle bundle;

    SharedPreferences.Editor prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        moviesRV = findViewById(R.id.moviesRV);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        bundle = new Bundle(this.getIntent().getExtras());
        email = bundle.get("email").toString();
        moviesRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));


        autoAuth();
        showMovies();

    }

    private void autoAuth(){
        prefs.putString("email", email);
        prefs.apply();
    }

    public void showMovies(){
        Call<List<Movie>> call = ApiClient.getClient().create(ApiMovie.class).getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    movies = response.body();
                    movieAdapter = new MovieAdapter(movies, getApplicationContext());
                    moviesRV.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "404 Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}