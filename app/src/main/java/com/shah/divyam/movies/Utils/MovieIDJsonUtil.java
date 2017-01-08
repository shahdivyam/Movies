package com.shah.divyam.movies.Utils;

import android.content.Intent;

import com.shah.divyam.movies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by divyam on 1/5/17.
 */

public class MovieIDJsonUtil {

    public static Movie getMovieDetailsFromJson(String strJson) throws JSONException {


        JSONObject rootJsonObj = new JSONObject(strJson);

        Movie movie = new Movie();

        movie.imgPath = rootJsonObj.getString("poster_path");
        movie.title = rootJsonObj.getString("original_title");
        Double rat = rootJsonObj.getDouble("vote_average");
        movie.rating = rat+"";
        movie.releaseDate = rootJsonObj.getString("release_date");
        movie.overview = rootJsonObj.getString("overview");

        return movie;
    }

    public static String[] getMovieIdFromJson(String strJson) throws JSONException {


        JSONObject rootJsonObj = new JSONObject(strJson);
        JSONArray listJsonArr = rootJsonObj.getJSONArray("results");
        int size = listJsonArr.length();

        String[] retval = new String[size];

        for(int i=0;i<size;i++){
            String movieId;
            JSONObject temp = listJsonArr.getJSONObject(i);
            int id = (int) temp.get("id");
            movieId = String.valueOf(id);
            retval[i] = movieId;
        }
        //check for completion of stage 1 , differentiator;

        return retval;

    }
}
