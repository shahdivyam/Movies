package com.shah.divyam.movies;

import com.shah.divyam.movies.Utils.MovieListAdapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener {

    RecyclerView mRvMovieList;
    MovieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvMovieList = (RecyclerView) findViewById(R.id.rv_movie_list_grid);
        mMovieListAdapter = new MovieListAdapter(MainActivity.this);


        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);

        mRvMovieList.setLayoutManager(manager);
        mRvMovieList.setAdapter(mMovieListAdapter);


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
                //pass;
                break;
            case R.id.action_top_rating:
                //pass;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        startActivity(intent);

        Toast.makeText(MainActivity.this, ": "+position, Toast.LENGTH_SHORT).show();
    }
}
