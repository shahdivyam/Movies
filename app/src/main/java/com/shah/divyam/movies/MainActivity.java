package com.shah.divyam.movies;

import com.shah.divyam.movies.Utils.*;
import com.shah.divyam.movies.Utils.NetworkUtils;
import com.shah.divyam.movies.Utils.MovieIDJsonUtil;



import android.content.Intent;
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
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener {

    String mApiKey = ApiKey.key;

    RecyclerView mRecyclerViewPopular;
    RecyclerView mRecyclerViewTopRated;

    MovieListAdapter mAdapterPopular;
    MovieListAdapter mAdapterTopRated;

    String[] mPopularMovieList ;
    String[] mTopRatedMovieList;

    Movie[] mDetailMovieListPopular;
    Movie[] mDetailMovieListTopRated;


    String popularMovieUrl = "http://api.themoviedb.org/3/movie/popular?api_key="+mApiKey ;
    String topRatedMovieUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key="+mApiKey;

    MovieListTask mPopularTask = new MovieListTask();
    MovieListTask mTopRatedTask = new MovieListTask();

    MovieDetailTask mMovieDetailTask = new MovieDetailTask();
    MovieDetailTask mMovieDetailTaskPop = new MovieDetailTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mPopularMovieList =  mPopularTask.execute(popularMovieUrl).get();

            mTopRatedMovieList = mTopRatedTask.execute(topRatedMovieUrl).get();

            mDetailMovieListPopular = mMovieDetailTask.execute(mPopularMovieList).get();
            mDetailMovieListTopRated = mMovieDetailTaskPop.execute(mTopRatedMovieList).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Divz",mPopularMovieList.length+" ");
        Log.d("Divz",mTopRatedMovieList.length+" ");


        mRecyclerViewPopular = (RecyclerView) findViewById(R.id.rv_movie_list_grid);
        mRecyclerViewTopRated = (RecyclerView) findViewById(R.id.rv_movie_list_grid_pop);

        mAdapterPopular = new MovieListAdapter(MainActivity.this,mDetailMovieListPopular,MainActivity.this);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        mRecyclerViewPopular.setLayoutManager(manager);
        mRecyclerViewPopular.setAdapter(mAdapterPopular);

        mAdapterTopRated = new MovieListAdapter(MainActivity.this,mDetailMovieListTopRated,MainActivity.this);
        GridLayoutManager managerTop = new GridLayoutManager(MainActivity.this,2);
        mRecyclerViewPopular.setLayoutManager(managerTop);
        mRecyclerViewPopular.setAdapter(mAdapterTopRated);




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
                mRecyclerViewTopRated.setVisibility(View.INVISIBLE);
                mRecyclerViewPopular.setVisibility(View.VISIBLE);
                break;
            case R.id.action_top_rating:
                mRecyclerViewPopular.setVisibility(View.INVISIBLE);
                mRecyclerViewTopRated.setVisibility(View.VISIBLE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = null;
        if(mRecyclerViewPopular.getVisibility()==View.VISIBLE) {
            movie = mDetailMovieListPopular[position];
        }
        else if(mRecyclerViewTopRated.getVisibility()==View.VISIBLE){
            movie = mDetailMovieListTopRated[position];
        }

        intent.putExtra("rating",movie.rating);
        intent.putExtra("overview",movie.overview);
        intent.putExtra("release",movie.releaseDate);
        intent.putExtra("title",movie.title);
        startActivity(intent);

        Toast.makeText(MainActivity.this, ": "+position, Toast.LENGTH_SHORT).show();
    }

    public class MovieListTask extends AsyncTask<String,Void,String[]>{

        public MovieListTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            //findViewById(R.id.loading_list_activity).setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String[] strings) {
            //findViewById(R.id.loading_list_activity).setVisibility(View.INVISIBLE);
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
        }

        @Override
        protected Movie[] doInBackground(String[]... params) {
            String[] movieId = params[0];

            int size = movieId.length;
            Movie[] retval = new Movie[size];

            for(int i=0;i<size-1;i++){
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
