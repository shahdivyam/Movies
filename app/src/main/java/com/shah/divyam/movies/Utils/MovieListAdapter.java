package com.shah.divyam.movies.Utils;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.shah.divyam.movies.MainActivity;
import com.shah.divyam.movies.Movie;
import com.shah.divyam.movies.R;
import com.squareup.picasso.Picasso;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {

    final ListItemClickListener mListItemClickListener;
    String halfUrl = "http://image.tmdb.org/t/p/w185/";
    String[] posterPath;
    Context context;
    Movie[] mMovieList;

    public MovieListAdapter(ListItemClickListener mListItemClickListener , Movie[]movieList, Context c) {
        this.mListItemClickListener = mListItemClickListener;
        this.context = c;
        mMovieList = movieList;
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
        ImageView imageView = holder.mImageView;
      new DownloadImageTask(imageView,holder.mImageProgress).execute("http://image.tmdb.org/t/p/w185/"+mMovieList[position].imgPath);


    }
    // change this value;
    @Override
    public int getItemCount() {
        return mMovieList.length;
    }

    public interface ListItemClickListener{
         void onClick(int position,Movie movie);
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mImageView;
        public ProgressBar mImageProgress;
        public MovieItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.imgv_movie_image);
            mImageProgress = (ProgressBar) itemView.findViewById(R.id.loading_image);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = mMovieList[position];
            mListItemClickListener.onClick(position,movie);
        }
    }

}