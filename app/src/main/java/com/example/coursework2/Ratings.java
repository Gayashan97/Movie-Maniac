package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class Ratings extends AppCompatActivity {

    private ListView movieList;
    private movieData movie;
    private List<String> allMovies = new ArrayList<String>();
    static String selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

//        List view of movies
        movieList = findViewById(R.id.listRatings);

//        To connect with the sqlite database
        movie = new movieData(this);

//        Gets all data from the movies database
        SQLiteDatabase db = movie.getReadableDatabase();
        Cursor cursor = db.query("registerMovie", movie.FROM, null, null, null, null, null);
        while (cursor.moveToNext()) {
//            Add movie titles into allMovies array
            allMovies.add(cursor.getString(1));
        }
        cursor.close();
//        Array adapter which contains all movies (with radio buttons)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, allMovies);
//        Set the array adapter into the list view
        movieList.setAdapter(arrayAdapter);
//        To make it allow to check radio buttons(single choice)
        movieList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        Actions to be done once the list view clicked
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Get selected movie title to pass to the child activity
                selectedMovie = (String) adapterView.getItemAtPosition(i);
            }
        });
    }

//    When click on the find movie in IMDB button
    public void findMovieInIMDB(View view) {
//        Call to start the child activity
        Intent intent = new Intent(Ratings.this, RatingsSelected.class);
        startActivity(intent);
    }
}