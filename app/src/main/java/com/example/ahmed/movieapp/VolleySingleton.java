package com.example.ahmed.movieapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bassem on 9/11/2016.
 */
public class VolleySingleton {

    public static VolleySingleton instance = null ;
    private RequestQueue requestQueue = null ;
    private static Context ctx ;

    private VolleySingleton(Context context){
        ctx = context ;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null)
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        return requestQueue;
    }

    public static VolleySingleton getInstance(Context context){

        if(instance == null)
            instance = new VolleySingleton(context);
        return instance ;
    }

    public void addToRequsetQueue(Request request){
        requestQueue.add(request);
    }

}
