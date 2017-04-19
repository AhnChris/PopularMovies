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
import android.widget.Spinner;

import java.util.ArrayList;

public class MainFragment extends Fragment implements FetchMovieTask.AsyncResultCallBack {

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void processData(ArrayList<MovieData> arrMovieData) {
        // Implement AsyncResultCallBack interface from FetchMovieTask

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
