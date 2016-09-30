package com.example.ahmed.movieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends android.app.Fragment {

    Adapter adapter;
    GridView gridView;
    Spinner spinner ;
    String movieJsonStr = null;
    Communicator communicator ;
    String popMoviesURL = "http://api.themoviedb.org/3/movie/popular?api_key=86d5400f79554bae52a9be7c2b9b59a8";
    String topRatedMoviesURL = "http://api.themoviedb.org/3/movie/top_rated?api_key=86d5400f79554bae52a9be7c2b9b59a8";
    String [] sortingChooser = {"pop movies" , "top rated movies"};
    boolean chooser = false;
    RequestQueue requestQueue ;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        List<Model> posters = new ArrayList<Model>();

        requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity() , android.R.layout.simple_spinner_dropdown_item , sortingChooser);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                moviesRequest(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        gridView = (GridView) v.findViewById(R.id.grid_view);
        adapter = new Adapter(getActivity(), posters);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                communicator.sendPositionAndJson(position, movieJsonStr);

                //Intent intent = new Intent(getActivity(), MovieDetails.class).putExtra(Intent.EXTRA_TEXT, movieJsonStr).putExtra("position", Integer.toString(position));
                //startActivity(intent);
            }
        });

        return v;
    }

    public void setCommunicator(Communicator communicator){
        this.communicator = communicator ;
    }

    public void fillAdapter(String [] posters){
        if (posters != null) {
                adapter.clear();
                for (String image : posters)
                    adapter.add(new Model(image));
            }
        else Toast.makeText(getActivity() , "null ya beeh" , Toast.LENGTH_LONG).show();
    }

    public void moviesRequest(int position){
       // final RequestQueue requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String URL  ;
        if(position == 1) URL = topRatedMoviesURL ;
        else URL = popMoviesURL ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                movieJsonStr = response ;

                try {
                    fillAdapter(new MovieParser(response).getPoster_path());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity() , "error " , Toast.LENGTH_LONG);
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }





}