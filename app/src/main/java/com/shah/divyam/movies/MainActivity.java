package com.shah.divyam.movies;

import com.shah.divyam.movies.Utils.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener {

    String mApiKey = ApiKey.key;

    public static int option;

    RecyclerView mRecyclerView;

    MovieListAdapter mAdapterPopular;
    MovieListAdapter mAdapterTopRated;

    String[] mPopularMovieList =null;
    String[] mTopRatedMovieList = null;
    Movie[] mDetailMovieListPopular = null;
    Movie[] mDetailMovieListTopRated = null;


    String popularMovieUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + mApiKey;
    String topRatedMovieUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + mApiKey;

    MovieListTask mPopularTask = new MovieListTask();
    MovieListTask mTopRatedTask = new MovieListTask();

    MovieDetailTask mMovieDetailTaskPop = new MovieDetailTask();
    MovieDetailTask mMovieDetailTaskTopRated = new MovieDetailTask();

    public static int pop = 0;
    public static int top = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setWindowTitle("Popular");


        try {
            mPopularMovieList = mPopularTask.execute(popularMovieUrl).get();
            mTopRatedMovieList = mTopRatedTask.execute(topRatedMovieUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        new MovieDetailTask().execute(mPopularMovieList);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch(itemID){
            case R.id.action_polpular:
                new MovieDetailTask().execute(mPopularMovieList);
                Toast.makeText(MainActivity.this, "Popular", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setWindowTitle("Popular Movies");
                break;
                
            case R.id.action_top_rating:
                Toast.makeText(MainActivity.this,"Top Rated,",Toast.LENGTH_SHORT).show();
                getSupportActionBar().setWindowTitle("Top Rated Movies");
                new MovieDetailTaskTopRated().execute(mTopRatedMovieList);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        // TODO bull's eye ;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position,Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);

        intent.putExtra("imgUrl","http://image.tmdb.org/t/p/w185/"+movie.imgPath);

        intent.putExtra("rating",movie.rating);
        intent.putExtra("overview",movie.overview);
        intent.putExtra("release",movie.releaseDate);
        intent.putExtra("title",movie.title);
        startActivity(intent);

    }

    public class MovieListTask extends AsyncTask<String,Void,String[]>{

        public MovieListTask() {
            super();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.loading_list_activity).setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }

        @Override
        protected String[] doInBackground(String... params) {
            String str = params[0];
            URL url = NetworkUtils.buildUrl(str);
            String strResponse =null;
            try {
                strResponse = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] retval=null;
            try {
                retval = MovieIDJsonUtil.getMovieIdFromJson(strResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retval;
        }
    }

    public class MovieDetailTask extends AsyncTask<String[],Void,Movie[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.loading_list_activity).setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);

                findViewById(R.id.loading_list_activity).setVisibility(View.INVISIBLE);

                mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list_grid);
                mAdapterPopular = new MovieListAdapter(MainActivity.this, movies, MainActivity.this);
                GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(mAdapterPopular);

        }

        @Override
        protected Movie[] doInBackground(String[]... params) {
            String[] movieId = params[0];

            int size = movieId.length;
            Movie[] retval = new Movie[size];

            for(int i=0;i<size;i++){
                String strUrl = "https://api.themoviedb.org/3/movie/"+movieId[i]+"?api_key="+ApiKey.key;
                URL url = NetworkUtils.buildUrl(strUrl);
                String strResponse =null;
                try {
                    strResponse = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    retval[i] = MovieIDJsonUtil.getMovieDetailsFromJson(strResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return retval;
        }
    }
    public class MovieDetailTaskTopRated extends AsyncTask<String[],Void,Movie[]>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.loading_list_activity).setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            super.onPostExecute(movies);

            findViewById(R.id.loading_list_activity).setVisibility(View.INVISIBLE);

            mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list_grid);
            mAdapterPopular = new MovieListAdapter(MainActivity.this, movies, MainActivity.this);
            GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(mAdapterPopular);

        }

        @Override
        protected Movie[] doInBackground(String[]... params) {
            String[] movieId = params[0];

            int size = movieId.length;
            Movie[] retval = new Movie[size];

            for(int i=0;i<size;i++){
                String strUrl = "https://api.themoviedb.org/3/movie/"+movieId[i]+"?api_key="+ApiKey.key;
                URL url = NetworkUtils.buildUrl(strUrl);
                String strResponse =null;
                try {
                    strResponse = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    retval[i] = MovieIDJsonUtil.getMovieDetailsFromJson(strResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return retval;
        }
    }
}
