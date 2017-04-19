package com.chrisahn.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable{
    private String mPosterPath;
    private String mReleaseDate;
    private String mOriginalTitle;
    private double mVoteAverage;
    private String mOverview;

    public MovieData() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPosterPath);
        dest.writeString(mReleaseDate);
        dest.writeString(mOriginalTitle);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mOverview);
    }

    public static final Parcelable.Creator<MovieData> CREATOR
            = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };

    private MovieData(Parcel in) {
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mOriginalTitle = in.readString();
        mVoteAverage = in.readDouble();
        mOverview = in.readString();
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }
}
