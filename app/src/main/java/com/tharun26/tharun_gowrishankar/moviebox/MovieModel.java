package com.tharun26.tharun_gowrishankar.moviebox;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Tharun_Gowrishankar on 3/16/2016.
 */
public class MovieModel {
    private String poster_path;
    private String original_title;
    private double popularity;
    private double vote_average;
    private String release_date;
    private String overview;

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

    public static MovieModel fromJson(JSONObject jsonObject)
    {
        MovieModel each_movie = new MovieModel();
        try {
            each_movie.vote_average = jsonObject.getDouble("vote_average");
            each_movie.popularity = jsonObject.getDouble("popularity");
            each_movie.poster_path = jsonObject.getString("poster_path");
            each_movie.original_title = jsonObject.getString("original_title");
        }catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }
        return each_movie;
    }

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


}
