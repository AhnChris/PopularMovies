package com.chrisahn.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chrisahn.popularmovies.model.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    List<MovieInfo> mMovieInfoList;

    public GridViewAdapter(Context context, List<MovieInfo> movieInfoList) {
        mContext = context;
        mMovieInfoList = movieInfoList;
    }

    @Override
    public int getCount() {
        return mMovieInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovieInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView gridImage;

        if (convertView == null) {
            // Create a new ImageView
            gridImage = new ImageView(mContext);
            gridImage.setAdjustViewBounds(true);
            gridImage.setPadding(0, 0, 0, 0);
        } else {
            // Recycled view
            gridImage = (ImageView) convertView;
        }

        // Load images into the ImageView
        String imageURL = "https://image.tmdb.org/t/p/w185"
                + mMovieInfoList.get(position).getPosterPath();
        Picasso.with(mContext).load(imageURL).error(R.drawable.error_img).into(gridImage);

        return gridImage;
    }

    public void setAdapterData(List<MovieInfo> movieInfoList) {
        mMovieInfoList = movieInfoList;
        notifyDataSetChanged();
    }
}
