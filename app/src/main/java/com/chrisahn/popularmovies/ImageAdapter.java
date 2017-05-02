package com.chrisahn.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<MovieData> {

    private final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;

    // Constructor
    public ImageAdapter(Context context, ArrayList<MovieData> movieDataList) {
        super(context, 0, movieDataList);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageView imageView;
        MovieData currentMovieData = getItem(position);

        // Check for recycled view
        if (convertView == null) {
            // Not recycled, set ImageView attributes
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true); // true always preserves aspect ratio
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        String BASE_URL_IMG = "https://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(BASE_URL_IMG + currentMovieData.getPosterPath()).into(imageView);

        Log.v(LOG_TAG, "URL used for image: " + BASE_URL_IMG + currentMovieData.getPosterPath());

        return imageView;
    }
}
