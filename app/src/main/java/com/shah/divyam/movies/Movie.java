package com.shah.divyam.movies;

/**
 * Created by divyam on 1/5/17.
 */
public class Movie {
    public String imgPath;
    public String title;
    public String rating;
    public String releaseDate;
    public String overview;

    public Movie(String imgPath, String title, String rating, String releaseDate, String overview) {
        this.imgPath = imgPath;
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }
    public Movie(){

    }

}
