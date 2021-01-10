package com.dennishc.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.dennishc.flixster.adapters.MovieAdapter;
import com.dennishc.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    // API Key Validation (Use link to see JSON Objects
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    // TAG for debugging (used in conjunction w/ Log)
    public static final String TAG = "MainActivity";

    // Array that holds movie objects
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        // Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        // Network library created by CodePath that we have imported
        AsyncHttpClient client = new AsyncHttpClient();

        // Use the Async Object
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json)
            {

                // Debug message letting us know we made it to this function
                Log.d(TAG, "onSuccess");

                // https://developer.android.com/reference/org/json/JSONObject
                JSONObject jsonObject = json.jsonObject;

                try
                {
                    //
                    JSONArray results = jsonObject.getJSONArray("results");

                    // Debug message (for RESULTS)
                    Log.i(TAG, "Results: " + results);

                    // Retrieve a list of movie objects
                    movies.addAll(Movie.fromJsonArray(results));

                    movieAdapter.notifyDataSetChanged();

                    // Debug message (for MOVIES)
                    Log.i(TAG, "Movies: " + movies.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable)
            {
                // Debug message letting us know we made it to this function
                Log.d(TAG, "onFailure");
            }

        }); // End of JsonHttpResponseHandler parameter
    }
}