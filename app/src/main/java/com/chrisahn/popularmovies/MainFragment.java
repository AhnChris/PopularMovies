package com.chrisahn.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainFragment extends Fragment implements FetchMovieTask.AsyncResultCallBack {

    private ImageAdapter mImageAdapter = null;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void processData(ArrayList<MovieData> arrMovieData) {
        // Implement AsyncResultCallBack interface from FetchMovieTask
        if (!arrMovieData.isEmpty()) {
            for (MovieData item : arrMovieData) {
                mImageAdapter.add(item);
            }
            mImageAdapter.notifyDataSetChanged();
        }

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

        FetchMovieTask.AsyncResultCallBack callback = this;
        FetchMovieTask fetchMovieTask = new FetchMovieTask(callback);
        fetchMovieTask.execute("popularity.desc");
        mImageAdapter = new ImageAdapter(getActivity(), new ArrayList<MovieData>());
        gridView.setAdapter(mImageAdapter);

        return rootView;
    }

}
