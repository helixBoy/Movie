package com.example.ahmed.movieapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Communicator{

    MainActivityFragment mainFragment ;
    MovieDetailsFragment detailedFragment ;
    FragmentManager fragmentManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        mainFragment =(MainActivityFragment) fragmentManager.findFragmentById(R.id.fragment1);
        mainFragment.setCommunicator(this);

    }

    public void sendPositionAndJson(int position, String json) {

        if(findViewById(R.id.fragment2)!=null ){

            detailedFragment = new MovieDetailsFragment();
            Bundle bundle = new Bundle() ;
            bundle.putInt("position", position);
            bundle.putString("json", json);

            detailedFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2 , detailedFragment).commit();

        }else {
            Intent intent = new Intent(MainActivity.this, MovieDetails.class).putExtra(Intent.EXTRA_TEXT, json).putExtra("position", Integer.toString(position));
            startActivity(intent);

        }
    }
}
