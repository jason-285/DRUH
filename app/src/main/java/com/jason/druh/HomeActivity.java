//--------------------------------------------------------------------------------------------------

/*IMPORTS*/

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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jason.druh.Model.Movie;
import com.jason.druh.Model.MovieAdapter;
import com.jason.druh.Network.ApiClient;
import com.jason.druh.Network.ApiMovie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class HomeActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    String email;
    List<Movie> movies;
    MovieAdapter movieAdapter;

    ImageButton searchHBtn, cinemaHBtn, logoutHBtn;
    RecyclerView moviesHRV;

    Bundle bundle;

    SharedPreferences.Editor prefs;

    //----------------------------------------------------------------------------------------------

    /*ONCREATE METHOD*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //------------------------------------------------------------------------------------------

        /*VARS INIT*/

        logoutHBtn = findViewById(R.id.logoutHBtn);
        cinemaHBtn = findViewById(R.id.cinemaHBtn);
        searchHBtn = findViewById(R.id.searchHBtn);
        moviesHRV = findViewById(R.id.moviesHRV);

        //------------------------------------------------------------------------------------------

        /*PRE-CHARGE METHODS*/

        prefs = getSharedPreferences(getString(R.string.prefs_file),
                Context.MODE_PRIVATE).edit();
        bundle = new Bundle(this.getIntent().getExtras());
        email = bundle.get("email").toString();
        moviesHRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        autoAuth();
        showMovies();

        //------------------------------------------------------------------------------------------

        /*LISTENER METHODS*/

        searchHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(HomeActivity.this,
                        SearchActivity.class);
                startActivity(searchIntent);
            }
        }); // END LISTENER

        cinemaHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinemasIntent = new Intent(HomeActivity.this,
                        CinemasActivity.class);
                startActivity(cinemasIntent);
            }
        }); // END LISTENER

        logoutHBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(authIntent);
            }
        }); // END LISTENER

        //------------------------------------------------------------------------------------------

    } // END ONCREATE

    //----------------------------------------------------------------------------------------------

    /*SETTINGS METHODS*/

    private void autoAuth(){
        prefs.putString("email", email);
        prefs.apply();
    } // END AUTOAUTH

    public void clickSetUp(){
        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie movie = movies.get(position);

                Intent movieIntent = new Intent(HomeActivity.this, MovieActivity.class);
                movieIntent.putExtra("Movie", movie);
                startActivity(movieIntent);
            }
        });
    } // END CLICKSETUP

    //----------------------------------------------------------------------------------------------

    /*API EXTRACTION METHODS*/

    public void showMovies(){
        Call<List<Movie>> call = ApiClient.getClient().create(ApiMovie.class).getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful()) {
                    movies = response.body();
                    movieAdapter = new MovieAdapter(movies, getApplicationContext());
                    clickSetUp();
                    moviesHRV.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "404 Error", Toast.LENGTH_SHORT).show();
            }
        });
    } // END SHOWMOVIES

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------