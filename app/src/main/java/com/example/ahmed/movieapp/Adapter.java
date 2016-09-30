package com.example.ahmed.movieapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class Adapter extends ArrayAdapter<Model> {
        private static final String LOG_TAG = Adapter.class.getSimpleName();


        public Adapter(Activity context, List<Model> movie) {
            super(context, 0, movie);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Model model = getItem(position);
            String url = model.image;
            String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.movie_image, parent, false);
            }

            ImageView iconView = (ImageView) convertView.findViewById(R.id.image);

            Picasso.with(getContext()).load(BASE_URL_IMG + url).into(iconView);

            return convertView;
        }
    }
