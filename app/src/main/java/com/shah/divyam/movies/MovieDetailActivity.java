package com.shah.divyam.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        imgPath = intent.getStringExtra("imgUrl");
        title = intent.getStringExtra("title");
        rating = intent.getStringExtra("rating");
        overview = intent.getStringExtra("overview");
        releaseDate = intent.getStringExtra("release");

        TextView tvrating = (TextView) findViewById(R.id.tv_rating);
        TextView tvoverview = (TextView) findViewById(R.id.tv_overview);
        TextView tvreleaseDate = (TextView) findViewById(R.id.tv_release_date);
        ImageView imgbanner = (ImageView) findViewById(R.id.imgv_poster_detail_activity);

        Picasso.with(this).setLoggingEnabled(true);

        Picasso.with(this).load(imgPath).resize(0,500).into(imgbanner);


        getSupportActionBar().setTitle(title);
        tvrating.setText(rating);
        tvoverview.setText(overview);
        tvreleaseDate.setText(releaseDate);







    }
}
