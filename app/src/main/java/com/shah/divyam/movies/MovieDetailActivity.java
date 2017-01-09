package com.shah.divyam.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
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

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Intent intent = getIntent();

        imgPath = intent.getStringExtra("imgUrl");
        title = intent.getStringExtra("title");
        rating = intent.getStringExtra("rating");
        overview = intent.getStringExtra("overview");
        releaseDate = intent.getStringExtra("release");

        TextView tvtitle = (TextView) findViewById(R.id.tv_title);
        TextView tvrating = (TextView) findViewById(R.id.tv_rating);
        TextView tvoverview = (TextView) findViewById(R.id.tv_overview);
        TextView tvreleaseDate = (TextView) findViewById(R.id.tv_release_date);
        ImageView imgbanner = (ImageView) findViewById(R.id.imgv_poster_detail_activity);

        Picasso.with(this).setLoggingEnabled(true);
        int w = (int) (width*0.4);

        Picasso.with(this).load(imgPath).resize(w,height/3).into(imgbanner);


        getSupportActionBar().setTitle("Movie Details");
        tvtitle.setText(title);
        tvrating.setText(rating);
        tvoverview.setText(overview);
        tvreleaseDate.setText(releaseDate);







    }
}
