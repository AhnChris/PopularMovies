package com.chrisahn.popularmovies.Network;

import com.chrisahn.popularmovies.model.DiscoverMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbInterface {

    @GET("discover/movie")
    Call<DiscoverMovieResponse> getMoviesWithSort(@Query("api_key") String api_key, @Query("sort_by") String sortParam);
}
