package com.jason.druh.Network;

import com.jason.druh.Model.Cinema;
import com.jason.druh.Model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCinema {
    @GET("movies/cinemas.php")

    Call<List<Cinema>> getCinemas();
}
