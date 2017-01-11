package com.shah.divyam.movies.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

/**
 * Created by divyam on 1/8/17.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressBar mImageProgress;

    public DownloadImageTask(ImageView bmImage,ProgressBar progressBar) {
        this.mImageProgress = progressBar;
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mImageProgress.setVisibility(View.VISIBLE);
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        Log.d("Divimage",urldisplay);
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        mImageProgress.setVisibility(View.INVISIBLE);

    }
}

