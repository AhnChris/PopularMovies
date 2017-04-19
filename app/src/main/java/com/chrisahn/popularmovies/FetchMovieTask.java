package com.chrisahn.popularmovies;

import android.os.AsyncTask;

import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<MovieData> > {

    // Interface to send data back to Fragment/Activity
    public interface AsyncResultCallBack {
        void processData(ArrayList<MovieData> arrMovieData);
    }

    @Override
    protected ArrayList<MovieData> doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> movieDatas) {
        super.onPostExecute(movieDatas);
    }
}
