package com.dennishc.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dennishc.flixster.models.Movie;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

   // public static final String YOUTUBE_API_KEY =

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);

        // Retrieve data from MovieAdapter.java
        // String title = getIntent().getStringExtra("title");
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        // Movie.getRating is a double, need a float
        ratingBar.setRating((float) movie.getRating());
    }
}