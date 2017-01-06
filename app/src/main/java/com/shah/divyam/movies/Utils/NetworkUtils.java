package com.shah.divyam.movies.Utils;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by divyam on 1/5/17.
 */
public class NetworkUtils {

    public static URL buildUrl(String str){

        URL url = null;
        try {
            url = new URL(str.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("Tag", "Built URI " + url);

        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()==true){
                return scanner.next();
            }
            else{
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }

    }

}
