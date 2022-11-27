package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RatingsSelected extends AppCompatActivity {

    private ListView allSelectedMovieList;
    private TextView selectedMovie;
    private List<String> allSelectedMovie = new ArrayList<>();
    static String selectedImdbMovie;
    static String selectedImdbMovieId;
    static String selectedImdbMovieImage;
    private List<String> idList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private String API_KEY = "k_jnjstt7j/";
//    private String API_KEY = "k_jnld32f9/";
    final String BASE_URL = "https://imdb-api.com/en/API/SearchTitle/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings_selected);

//        Call the views of xml file
        allSelectedMovieList = findViewById(R.id.listRatingsSelected);
        selectedMovie = findViewById(R.id.textSelectedRatings);

//        Set the movie title taken from the parent activity
        selectedMovie.setText(Ratings.selectedMovie);

//        URL to get the json file from IMDB API
        Uri builtURI = Uri.parse(BASE_URL + API_KEY + selectedMovie.getText()).buildUpon().build();
        String myUrl = builtURI.toString();
//        To load the movie list form IMDB API
        new RatingsSelected.LoadMovieList().execute(myUrl);
    }

//    To load the movie list form IMDB API
    public class LoadMovieList extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder("");

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
                JSONArray jsonArray = jsonObject.getJSONArray("results");

//                To get all needed json data
                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject element = jsonArray.getJSONObject(i);
                    allSelectedMovie.add(element.getString("title"));
                    idList.add(element.getString("id"));
                    imageList.add(element.getString("image"));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
//            Return the list of movie titles from IMDB API
            return allSelectedMovie;
        }

        protected void onPostExecute(List<String> s) {
            super.onPostExecute(s);
            lmdbData(s);
        }
    }

    private void lmdbData(List<String> imdbMovies) {
//        Array adapter which contains returned API movie titles
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, imdbMovies);
//        Set the array adapter into the list view
        allSelectedMovieList.setAdapter(arrayAdapter);
//        Actions to be done once the list view clicked
        allSelectedMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Movie title, id and image to pass to the child activity
                selectedImdbMovie = (String) adapterView.getItemAtPosition(i);
                selectedImdbMovieId = idList.get(i);
                selectedImdbMovieImage = imageList.get(i);

//                Call to start the child activity
                Intent intent = new Intent(RatingsSelected.this, RatingsImage.class);
                startActivity(intent);
            }
        });
    }
}