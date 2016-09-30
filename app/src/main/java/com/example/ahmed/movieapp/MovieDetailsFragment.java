package com.example.ahmed.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    Button playTrailerButton1 ;
    Button playTrailerButton2 ;
    ImageButton favoriteButton ;
    MovieParser movieParser =null;
    String JSONStr = null ;
    TextView title ;
    ImageView imageView ;
    TextView releaseDate ;
    TextView userRating ;
    TextView description ;
    Intent intent;
    int position ;
    String id  ;
    ArrayList<String> favorites = new ArrayList<String>() ;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";


    public MovieDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "foooo2", Toast.LENGTH_LONG).show();
        Bundle bundle = getArguments();
        position =  bundle.getInt("position");
        JSONStr = bundle.getString("json");
        Toast.makeText(getActivity(), "recieved", Toast.LENGTH_LONG).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_movie_details, container, false);

       // initializeData();
        initializeViews(v);
        initializeParser();
        setViews();
        movieTrailerRequest(position);
        movieReviewsRequest(position);
        //favoriteButtonAction();
        //initializeFavorites();

        return v ;
    }

    private void initializeData(){
        sharedPreferences = getActivity().getSharedPreferences("favorites", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        saveAndcommit();

    }

    private void initializeViews(View v){
        title = (TextView)v.findViewById(R.id.title);
        imageView = (ImageView) v.findViewById(R.id.poster);
        releaseDate = (TextView) v.findViewById(R.id.release_date);
        userRating = (TextView) v.findViewById(R.id.user_rating);
        description = (TextView) v.findViewById(R.id.description);
        playTrailerButton1 = (Button) v.findViewById(R.id.trailer1);
        playTrailerButton2 =(Button) v.findViewById(R.id.trailer2);
        favoriteButton = (ImageButton) v.findViewById(R.id.favorite_button);
    }

    private void setViews (){
        try {
            title.setText(movieParser.getTitle()[position]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Picasso.with(getContext()).load(BASE_URL_IMG + movieParser.getPoster_path()[position]).into(imageView);
            }
            releaseDate.setText(movieParser.getRelease_date()[position]);
            userRating.setText(movieParser.getUser_rating()[position]);
            description.setText(movieParser.getPlot_synopsis()[position]);

        }catch (Exception e){} ;
    }

    private void initializeParser(){
        try {
            movieParser = new MovieParser(JSONStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getIntent() {
        intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            JSONStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            position = Integer.parseInt(intent.getStringExtra("position"));
        }
    }

    public void getMyData(int position , String json){
        this.position = position ;
        this.JSONStr = json ;
    }

    private void playTrailer1(final String trailer){
        playTrailerButton1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer)));

            }
        });
    }

    private void playTrailer2(final String trailer){
        playTrailerButton2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer)));
            }
        });
}

    public void movieTrailerRequest(int position){

        try {
            id = movieParser.getId()[position];
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=86d5400f79554bae52a9be7c2b9b59a8", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("volleyy" , "came");
                    playTrailer1(YOUTUBE_BASE_URL + new MovieParser(response).getTrailers()[0]);
                    playTrailer2(YOUTUBE_BASE_URL + new MovieParser(response).getTrailers()[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error ", Toast.LENGTH_LONG);
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void movieReviewsRequest(int position){

        try {
            id = movieParser.getId()[position];
            Log.d("position" , "position is : " + id);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=86d5400f79554bae52a9be7c2b9b59a8", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                   Log.d("reviews", new MovieParser(response).getReviews()[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error ", Toast.LENGTH_LONG);
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void favoriteButtonAction(){
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favoriteButton.setSelected(!favoriteButton.isSelected());

                favorites = getFavoritesFromSharedPreferences();

                if (favoriteButton.isSelected()) {
                    favoriteButton.setBackgroundResource(android.R.drawable.btn_star_big_on);
                    favorites.add(id);
                    saveAndcommit();
                } else {
                    favoriteButton.setBackgroundResource(android.R.drawable.btn_star_big_off);
                    removeFavorite();
                    saveAndcommit();
                }

                favorites = getFavoritesFromSharedPreferences();
                String fav = "";
                for (int i = 0; i < favorites.size(); ++i) {
                    fav += favorites.get(i) + "\n";
                }
                Toast.makeText(getActivity(), fav, Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void initializeFavorites(){
        ArrayList arrayList = getFavoritesFromSharedPreferences();
        for(int i=0 ; i<arrayList.size() ; ++i){
            if(arrayList.get(i).equals(id))
           {
               favoriteButton.setBackgroundResource(android.R.drawable.btn_star_big_on);
               favoriteButton.setSelected(true);
           }

        }
    }

    private void removeFavorite(){
        for (int i = 0; i < favorites.size(); ++i) {
            if (favorites.get(i).equals(id)) {
                favorites.remove(i);
                break;
            }
        }
    }

    private void saveAndcommit(){
        editor.putString("favorites", new Gson().toJson(favorites));
        editor.commit();
    }

    private ArrayList getFavoritesFromSharedPreferences(){
        return new Gson().fromJson(sharedPreferences.getString("favorites", null), ArrayList.class);
    }

}