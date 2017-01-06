package com.shah.divyam.movies.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shah.divyam.movies.MainActivity;
import com.shah.divyam.movies.Movie;
import com.shah.divyam.movies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by divyam on 1/5/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {

    final ListItemClickListener mListItemClickListener;
    String halfUrl = "http://image.tmdb.org/t/p/w185/";
    String[] posterPath;
    Context context;
    Movie[] mMovieList;
    //constructor
    public MovieListAdapter(ListItemClickListener mListItemClickListener , Movie[]movieId, Context c) {
        this.mListItemClickListener = mListItemClickListener;
        this.context = c;
        mMovieList = movieId;
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layouIdForListItem = R.layout.movie_list_item_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layouIdForListItem,parent,false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.MovieItemViewHolder holder, int position) {
        ImageView iv = holder.mImageView;
        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+mMovieList[position].imgPath).into(iv);
        iv.setImageResource(R.drawable.sample2);

        Log.d("Hey",position + " : " + mMovieList[position].imgPath);
    }
    // change this value;
    @Override
    public int getItemCount() {
        return 12;
    }

    public interface ListItemClickListener{
        public void onClick(int position);
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.imgv_movie_image);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListItemClickListener.onClick(position);
        }
    }

}