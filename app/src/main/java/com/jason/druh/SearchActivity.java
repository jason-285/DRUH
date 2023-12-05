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

//--------------------------------------------------------------------------------------------------

/*CLASS*/

public class SearchActivity extends AppCompatActivity {

    //----------------------------------------------------------------------------------------------

    /*VARS DEFINITION*/

    List<Movie> foundMovies;
    MovieAdapter movieAdapter;

    ImageButton backSBtn, cinemaSBtn, logoutSBtn,searchSBtn;
    EditText searchInput;
    Space titleSMar;
    TextView titleSTxtV;
    RecyclerView moviesSRV;

    SharedPreferences.Editor prefs;

    //----------------------------------------------------------------------------------------------

    /*ONCREATE METHOD*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //------------------------------------------------------------------------------------------

        /*VARS INIT*/

        backSBtn = findViewById(R.id.backSBtn);
        cinemaSBtn = findViewById(R.id.cinemaSBtn);
        logoutSBtn = findViewById(R.id.logoutSBtn);
        searchSBtn = findViewById(R.id.searchSBtn);
        searchInput = findViewById(R.id.searchInput);
        titleSMar = findViewById(R.id.titleSMar);
        titleSTxtV = findViewById(R.id.titleSTxtV);
        moviesSRV = findViewById(R.id.moviesSRV);

        //------------------------------------------------------------------------------------------

        /*PRE-CHARGE METHODS*/

        prefs = getSharedPreferences(getString(R.string.prefs_file),
                Context.MODE_PRIVATE).edit();
        moviesSRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        showMovies();

        //------------------------------------------------------------------------------------------

        /*LISTENER METHODS*/

        backSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        }); // END LISTENER

        cinemaSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cinemaIntent = new Intent(SearchActivity.this,
                        CinemasActivity.class);
                startActivity(cinemaIntent);
            }
        }); // END LISTENER

        logoutSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.clear();
                prefs.apply();
                FirebaseAuth.getInstance().signOut();
                Intent authIntent = new Intent(SearchActivity.this,
                        AuthActivity.class);
                startActivity(authIntent);
            }
        }); // END LISTENER

        searchSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMovies();
            }
        }); // END LISTENER

        //------------------------------------------------------------------------------------------

    } // END ONCREATE

    //----------------------------------------------------------------------------------------------

    /*HELPERS METHODS*/

    public void setSTitle(String title){
        titleSTxtV.setText(title);
    } // END SETTITLE

    public List<Movie> listPurge(List<Movie> list){
        List<Movie> newList = new ArrayList<Movie>();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getTitle().toString().contains(searchInput.getText().toString())){
                newList.add(list.get(i));
            }
        }

        setSTitle("Results for \"" +searchInput.getText().toString()+"\"");

        return newList;
    } // END LISTPURGE

    //----------------------------------------------------------------------------------------------

    /*SETTINGS METHODS*/

    public void clickSetUp(){
        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie movie = foundMovies.get(position);

                Intent movieIntent = new Intent(SearchActivity.this,
                        MovieActivity.class);
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
                    setSTitle("All Movies");
                    foundMovies = response.body();

                    if (!searchInput.getText().toString().isEmpty()){
                        foundMovies = listPurge(foundMovies);
                    }

                    movieAdapter = new MovieAdapter(foundMovies, getApplicationContext());
                    clickSetUp();
                    moviesSRV.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                setSTitle("Searching Error");
                Toast.makeText(SearchActivity.this, "404 Error", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    } // END SHOWMOVIES

    //----------------------------------------------------------------------------------------------

} // END CLASS

//--------------------------------------------------------------------------------------------------