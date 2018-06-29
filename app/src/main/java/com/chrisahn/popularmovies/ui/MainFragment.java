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
    private static final String SPINNER_POSITION_TAG = "SPINNER_POSITION_TAG";

    private GridViewAdapter mGridAdapter;
    private List<MovieInfo> mMovieInfoList;

    private boolean mFromSavedSpinnerState = false;
    private int mSpinnerPosition;

    public MainFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Clear any existing menu
        menu.clear();

        // Inflate the menu menu layout
        inflater.inflate(R.menu.fragment_main_menu, menu);
        // Grab the spinner menu item and set spinner
        MenuItem menuItem = menu.findItem(R.id.main_fragment_spinner_item);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);

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
                // Check if we have a saved spinner state
                if (mFromSavedSpinnerState) {
                    mFromSavedSpinnerState = false;
                    spinner.setSelection(mSpinnerPosition);

                    Log.d(LOG_TAG, "Resumed previous spinner state at position: " + mSpinnerPosition);
                } else {
                    mSpinnerPosition = position;

                    Log.d(LOG_TAG, "Position: " + mSpinnerPosition + " clicked");
                }

                String sortParam = getResources().getStringArray(R.array.sort_query_param)[mSpinnerPosition];
                getMovieListings(sortParam);
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


        mMovieInfoList = new ArrayList<>();

        // Set the adapter for the GridView
        mGridAdapter = new GridViewAdapter(getContext(), mMovieInfoList);
        gridView.setAdapter(mGridAdapter);

        // Set an ItemClickListener for the GridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), position + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Check if we have any saved-state data
        if (savedInstanceState != null) {
            mSpinnerPosition = savedInstanceState.getInt(SPINNER_POSITION_TAG);
            mFromSavedSpinnerState = true;
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SPINNER_POSITION_TAG, mSpinnerPosition);
        super.onSaveInstanceState(outState);

        Log.d(LOG_TAG, "Saved spinner position state of: " + mSpinnerPosition);
    }

    private void getMovieListings(String sortParam) {

        // Get a response from the DB API
        MovieDbInterface dbInterface = MovieDbAPIClient.getClient().create(MovieDbInterface.class);
        Call<DiscoverMovieResponse> call =
                dbInterface.getMoviesWithSort(BuildConfig.MOVIE_DB_KEY, sortParam);
        call.enqueue(new Callback<DiscoverMovieResponse>() {
            @Override
            public void onResponse(Call<DiscoverMovieResponse> call, Response<DiscoverMovieResponse> response) {
                mMovieInfoList = response.body().getMovieInfoList();
                // Update gridview adapter with new data
                mGridAdapter.setAdapterData(mMovieInfoList);

                Log.d(LOG_TAG, "Successful request: " + call.request().url().toString());
                Log.d(LOG_TAG, "Results came back with size of: " + mMovieInfoList.size());

            }

            @Override
            public void onFailure(Call<DiscoverMovieResponse> call, Throwable t) {
                Log.d(LOG_TAG, "Failed with request: " + call.request().url().toString());
                Log.d(LOG_TAG, t.getMessage());
            }
        });
    }
}
