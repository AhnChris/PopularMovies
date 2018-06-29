package com.chrisahn.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverMovieResponse implements Parcelable{

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<MovieInfo> movieInfoList = null;
    public final static Parcelable.Creator<DiscoverMovieResponse> CREATOR = new Creator<DiscoverMovieResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DiscoverMovieResponse createFromParcel(Parcel in) {
            return new DiscoverMovieResponse(in);
        }

        public DiscoverMovieResponse[] newArray(int size) {
            return (new DiscoverMovieResponse[size]);
        }

    };

    protected DiscoverMovieResponse(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.movieInfoList, (com.chrisahn.popularmovies.model.MovieInfo.class.getClassLoader()));
    }

    public DiscoverMovieResponse() {

    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieInfo> getMovieInfoList() {
        return movieInfoList;
    }

    public void setMovieInfoList(List<MovieInfo> movieInfoList) {
        this.movieInfoList = movieInfoList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(movieInfoList);
    }

    public int describeContents() {
        return 0;
    }
}
