package com.tharun26.tharun_gowrishankar.moviebox;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tharun_Gowrishankar on 3/16/2016.
 *
 * Movie Model class represents a movie
 * It contains the details of a individual movie
 * Object is constructed after Retriving the json from server
 *
 */

public class MovieModel implements Parcelable{
    private String poster_path;
    private String backdrop_path;
    private String original_title;
    private double popularity;
    private double vote_average;
    private String release_date;
    private String overview;

    public  MovieModel()
    {}
    public MovieModel(Parcel in) {
        this.original_title = in.readString();
        this.overview = in.readString();
        this.poster_path = in.readString();
        this.release_date = in.readString();
        this.popularity = in.readDouble();
        this.vote_average = in.readDouble();
        this.backdrop_path = in.readString();
    }


    /*
    * Getter and set Methods
    *
    */
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {

        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /*
    * Given a json object: Item of a result array in json file
    * Converts json information into object
    * Returns: Individual Movie object
    */
    public static MovieModel fromJson(JSONObject jsonObject)
    {
        MovieModel each_movie = new MovieModel();
        try {
            each_movie.setVote_average(jsonObject.getDouble("vote_average"));
            each_movie.setPopularity(jsonObject.getDouble("popularity"));
            each_movie.setPoster_path(jsonObject.getString("poster_path"));
            each_movie.setOriginal_title(jsonObject.getString("original_title"));
            each_movie.setOverview(jsonObject.getString("overview"));
            each_movie.setRelease_date(jsonObject.getString("release_date"));
            each_movie.setBackdrop_path(jsonObject.getString("backdrop_path"));

        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return each_movie;
    }

    /*
    * Given a json array - Uses fromJson to retrive individual movie details
    * Returns: List of Movie Model Objects
    */
    public static ArrayList<MovieModel> fromJsonArray(JSONArray jsonArray)
    {
        ArrayList<MovieModel> list_all_movies;
        list_all_movies = new ArrayList<MovieModel>(jsonArray.length());
        try
        {
            for(int i=0;i<jsonArray.length();i++) {
                MovieModel mm = fromJson(jsonArray.getJSONObject(i));
                if (mm !=null) {
                    Log.i("from_json_array","");
                    list_all_movies.add(mm);
                }
                }
        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return list_all_movies;
    }

    /*
    Defines object
    */
    @Override
    public int describeContents() {
        return this.hashCode();
    }
    /*

    */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(original_title);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeDouble(popularity);
        dest.writeDouble(vote_average);
    }
    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>(){
        public MovieModel createFromParcel(Parcel in)
        {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public int hashCode() {
        return (this.getOriginal_title().hashCode());
    }
}
