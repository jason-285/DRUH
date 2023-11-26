package com.jason.druh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Movie;
import com.jason.druh.Model.MovieAdapter;
import com.jason.druh.Network.ApiClient;
import com.jason.druh.Network.ApiMovie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    List<Movie> foundMovies;
    MovieAdapter movieAdapter;


    ImageButton homeSBtn,logoutSBtn,searchSBtn;
    EditText searchInput;
    Space titleSMar;
    TextView titleSTxtV;
    RecyclerView moviesSRV;

    SharedPreferences.Editor prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        logoutSBtn = findViewById(R.id.logoutSBtn);
        homeSBtn = findViewById(R.id.homeSBtn);
        searchSBtn = findViewById(R.id.searchSBtn);
        searchInput = findViewById(R.id.searchInput);
        titleSMar = findViewById(R.id.titleSMar);
        titleSTxtV = findViewById(R.id.titleSTxtV);
        moviesSRV = findViewById(R.id.moviesSRV);

        prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
        moviesSRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        showMovies();

        homeSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logoutSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(SearchActivity.this, AuthActivity.class);
                startActivity(authIntent);
            }
        });

        searchSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovies();
            }
        });
    }

    public void setSTitle(String title){
        titleSTxtV.setText(title);
    }

    public List<Movie> listPurge(List<Movie> list){
        List<Movie> newList = new ArrayList<Movie>();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getTitle().toString().contains(searchInput.getText().toString())){
                newList.add(list.get(i));
            }
        }

        setSTitle('"'+searchInput.getText().toString()+'"');

        return newList;
    }

    public void showMovies(){
        Call<List<Movie>> call = ApiClient.getClient().create(ApiMovie.class).getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    setSTitle("All Movies");
                    foundMovies = response.body();

                    if (!searchInput.getText().toString().isEmpty()){
                        foundMovies = listPurge(foundMovies);
                    }

                    movieAdapter = new MovieAdapter(foundMovies, getApplicationContext());
                    moviesSRV.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                setSTitle("Searching Error");
                Toast.makeText(SearchActivity.this, "404 Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}