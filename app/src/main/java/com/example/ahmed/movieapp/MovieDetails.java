package com.example.ahmed.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = new Bundle();
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String JSONStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            int position = Integer.parseInt(intent.getStringExtra("position"));
            bundle.putInt("position" , position);
            bundle.putString("json", JSONStr);
            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            movieDetailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2 , movieDetailsFragment).commit();
        }

    }

}
