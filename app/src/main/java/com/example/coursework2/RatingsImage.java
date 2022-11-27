package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RatingsImage extends AppCompatActivity {

    private TextView movieTitle;
    private TextView movieRating;
    private ImageView movieImage;
//    private String API_KEY = "k_jnld32f9/";
    private String API_KEY = "k_jnjstt7j/";
    final String BASE_URL = "https://imdb-api.com/en/API/UserRatings/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_image);

//        Call the views of xml file
        movieTitle = findViewById(R.id.textImageRatings);
        movieRating = findViewById(R.id.textImageRatingsMovie);
        movieImage = findViewById(R.id.imageRatings);

//        Set the movie title taken from the parent activity
        movieTitle.setText(RatingsSelected.selectedImdbMovie);

//        To load the movie image form IMDB API
        new RatingsImage.LoadImage().execute();

//        URL to get the json file from IMDB API
        Uri builtURI = Uri.parse(BASE_URL + API_KEY + RatingsSelected.selectedImdbMovieId).buildUpon().build();
        String myUrl = builtURI.toString();
//        To load the movie rating form IMDB API
        new RatingsImage.LoadRating().execute(myUrl);
    }

//    To load the movie image form IMDB API
    public class LoadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
//            Get the image url from the parent activity
            String imageUrl = RatingsSelected.selectedImdbMovieImage;
            Bitmap bitmap = null;
            try {
//                Set the image url to the bitmap
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Return the image of the selected movie form IMDB API
            return bitmap;
        }

        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
//            Set the image
            movieImage.setImageBitmap(s);
        }
    }

//    To load the movie rating form IMDB API
    public class LoadRating extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder("");
            StringBuilder stringBuilderParseResults = new StringBuilder("");

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                if (stringBuilder.length() == 0) {
                    return null;
                }
//                To display the json file under run tab
                Log.i("StringBuffer Contains :", stringBuilder.toString());

//                Call json objects
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
//                To get the rating from json data
                stringBuilderParseResults.append("Rating: " + jsonObject.getString("totalRating"));


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
//            return the rating of the selected movie from IMDB API
            return stringBuilderParseResults.toString();
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Set the rating
            movieRating.setText(s);
        }
    }
}