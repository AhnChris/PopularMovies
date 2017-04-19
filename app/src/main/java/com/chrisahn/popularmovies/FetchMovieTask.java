package com.chrisahn.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<MovieData> > {

    private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

    // Interface to send data back to Fragment/Activity
    public interface AsyncResultCallBack {
        void processData(ArrayList<MovieData> arrMovieData);
    }

    @Override
    protected ArrayList<MovieData> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        String jsonResponseStr;

        try {
            final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
            final String SORT_PARAM = "sort_by";
            final String API_KEY_PARAM = "api_key";

            // Build URI
            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_PARAM, params[0])
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_KEY)
                    .build();

            // Convert to URL and Log current URL
            URL url = new URL(uri.toString());
            Log.v(LOG_TAG, "Final URL: " + uri.toString());

            // Create request to Movie DB API and open connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Gather InputStream data and read into the BufferedReader
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer jsonStringBuffer = new StringBuffer();

            // Check for a response from connection
            if (inputStream == null)
            {
                Log.e(LOG_TAG, "Empty InputStream, no response");
                return null;
            }

            // Read InputStream into BufferedReader
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Read each line of bufferedReader and append into jsonStringBuffer
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Append with new-line for readability
                jsonStringBuffer.append(line + "\n");
            }

            if (jsonStringBuffer.length() == 0) {
                // Empty Stream
                Log.e(LOG_TAG, "Empty StringBuffer");
                return null;
            }

            jsonResponseStr = jsonStringBuffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            // Close connections
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> movieDatas) {
        super.onPostExecute(movieDatas);
    }
}
