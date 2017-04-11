package com.chrisahn.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieContainer implements Parcelable{
    private String mPosterPath;
    private String mReleaseDate;
    private String mOriginalTitle;
    private float mVoteAverage;
    private String mOverview;

    public MovieContainer() {

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
        dest.writeFloat(mVoteAverage);
        dest.writeString(mOverview);
    }

    public static final Parcelable.Creator<MovieContainer> CREATOR
            = new Parcelable.Creator<MovieContainer>() {
        public MovieContainer createFromParcel(Parcel in) {
            return new MovieContainer(in);
        }

        public MovieContainer[] newArray(int size) {
            return new MovieContainer[size];
        }
    };

    private MovieContainer(Parcel in) {
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mOriginalTitle = in.readString();
        mVoteAverage = in.readFloat();
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

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }
}
