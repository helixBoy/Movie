package com.example.ahmed.movieapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bassem on 8/13/2016.
 */
public class MovieParser {

    // json object to be parsed
    private JSONObject jsonObject = null;

    //extracted data
    private JSONArray results = null ;// json array contains movies details

    String [] poster_path   = new String[20] ;
    String [] title         = new String[20] ;
    String [] plot_synopsis = new String[20] ;
    String[] user_rating    = new String[20] ;
    String [] release_date  = new String[20] ;
    String [] id            = new String[20] ;
    String [] trailers      = new String[10] ;
    String [] reviews       = new String[1000] ;


    // keys
    private String results_key = "results" ;

    private String poster_path_key = "poster_path" ;
    private String title_key = "title" ;
    private String plot_synopsis_key = "overview" ;
    private String user_rating_key = "vote_average" ;
    private String release_date_key = "release_date" ;
    private String id_key = "id";
    private String trailers_key = "key";
    private String reviews_key  = "content";


    public MovieParser(String jsonStr) throws JSONException {
        this.jsonObject = new JSONObject(jsonStr) ;
    }

    // return posters_path
    public String [] getPoster_path() throws JSONException {
        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            poster_path[i] = movieObject.getString(poster_path_key);
        }

        return poster_path ;
    }

    //return titles
    public String [] getTitle() throws JSONException {

        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            title[i] = movieObject.getString(title_key);
        }

        return title;
    }

    //return plot synopsis
    public String [] getPlot_synopsis() throws JSONException {

        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            plot_synopsis[i] = movieObject.getString(plot_synopsis_key);
        }

        return plot_synopsis;
    }

    //return user ratings
    public String [] getUser_rating() throws JSONException {

        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            user_rating[i] = movieObject.getString(user_rating_key);
        }

        return user_rating;
    }

    //return release date
    public String [] getRelease_date() throws JSONException {

        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            release_date[i] = movieObject.getString(release_date_key);
        }

        return release_date;
    }

    //return id
    public String [] getId() throws JSONException {

        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            id[i] = Integer.toString(movieObject.getInt(id_key));
        }

        return id;
    }

    //return trailer key
    public String[] getTrailers() throws JSONException {
        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            trailers[i] = movieObject.getString(trailers_key);
        }

        return trailers ;
    }

    //return reviews
    public String[] getReviews() throws JSONException {
        results = this.jsonObject.getJSONArray(results_key);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieObject = results.getJSONObject(i);
            reviews[i] = movieObject.getString(reviews_key);
        }

        return reviews ;
    }

}
