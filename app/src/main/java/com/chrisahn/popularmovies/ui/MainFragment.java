package com.chrisahn.popularmovies.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chrisahn.popularmovies.BuildConfig;
import com.chrisahn.popularmovies.GridViewAdapter;
import com.chrisahn.popularmovies.Network.MovieDbAPIClient;
import com.chrisahn.popularmovies.Network.MovieDbInterface;
import com.chrisahn.popularmovies.R;
import com.chrisahn.popularmovies.model.DiscoverMovieResponse;
import com.chrisahn.popularmovies.model.MovieInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();
    private GridViewAdapter mGridAdapter;

    public MainFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu menu layout
        inflater.inflate(R.menu.fragment_main_menu, menu);
        // Grab the spinner menu item and set spinner
        MenuItem menuItem = menu.findItem(R.id.main_fragment_spinner_item);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);

        // TODO: Possibly make a custom spinner item & drop-down item layout
        // Set spinner string array and use default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getActivity()
                                                                    ,R.array.sort_options
                                                                    ,android.R.layout.simple_spinner_item);
        // Use default spinner drop-down menu layout
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Set an ItemSelectedListener for the Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Most popular sort was picked
                    getMovieListings("popularity.desc");

                    Log.d(LOG_TAG, "Sorting by most popular");
                } else {
                    getMovieListings("vote_average.desc");

                    Log.d(LOG_TAG, "Sorting by highest vote average");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(LOG_TAG, "Nothing was selected from the spinner");
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate main fragment layout
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movieGrid);

        // Set the adapter for the GridView
        mGridAdapter= new GridViewAdapter(getContext(), new ArrayList());
        gridView.setAdapter(mGridAdapter);

        // Set an ItemClickListener for the GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void getMovieListings(String sortParam) {

        // Get a response from the DB API
        MovieDbInterface dbInterface = MovieDbAPIClient.getClient().create(MovieDbInterface.class);
        Call<DiscoverMovieResponse> call =
                dbInterface.getMoviesWithSort(BuildConfig.MOVIE_DB_KEY, sortParam);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
                List<MovieInfo> movieInfoList = response.body().getMovieInfoList();
                // Update gridview adapter with new data
                mGridAdapter.setAdapterData(movieInfoList);

                Log.d(LOG_TAG, "Successful request: " + call.request().url().toString());
                Log.d(LOG_TAG, "Results came back with size of: " + movieInfoList.size());

            }

            @Override
            public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
                Log.d(LOG_TAG, "Failed with request: " + call.request().url().toString());
                Log.d(LOG_TAG, t.getMessage());
            }
        });
    }
}
