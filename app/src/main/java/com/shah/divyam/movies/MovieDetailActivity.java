package com.shah.divyam.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity {

    public String imgPath;
    public String title;
    public String rating;
    public String releaseDate;
    public String overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        rating = intent.getStringExtra("rating");
        overview = intent.getStringExtra("overview");
        releaseDate = intent.getStringExtra("release");

        TextView tvrating = (TextView) findViewById(R.id.tv_rating);
        TextView tvoverview = (TextView) findViewById(R.id.tv_overview);
        TextView tvreleaseDate = (TextView) findViewById(R.id.tv_release_date);

        getSupportActionBar().setTitle(title);
        tvrating.setText(rating);
        tvoverview.setText(overview);
        tvreleaseDate.setText(releaseDate);



    }
}
