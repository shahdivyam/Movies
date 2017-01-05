package com.shah.divyam.movies.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shah.divyam.movies.R;

/**
 * Created by divyam on 1/5/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {

    final ListItemClickListener mListItemClickListener;

    public MovieListAdapter(ListItemClickListener mListItemClickListener) {
        this.mListItemClickListener = mListItemClickListener;
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
        holder.mImageView.setImageResource(R.drawable.sample2);

    }
    @Override
    public int getItemCount() {
        return 10;
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
