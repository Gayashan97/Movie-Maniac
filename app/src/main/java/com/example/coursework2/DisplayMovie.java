package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class DisplayMovie extends AppCompatActivity {

    private movieData movie;
    private ListView movieList;
    private List<String> allMovies = new ArrayList<String>();
    private List<String> favMovies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

//        List view of movies
        movieList = findViewById(R.id.listDisplayMovies);

//        To connect with the sqlite database
        movie = new movieData(this);

//        Gets all data from the movies database
        SQLiteDatabase db = movie.getReadableDatabase();
        Cursor cursor = db.query("registerMovie", movie.FROM, null, null, null, null, null);
        while (cursor.moveToNext()) {
//            To add all movie titles into the allMovies list
            allMovies.add(cursor.getString(1));
        }
        cursor.close();
//        To sort movie titles in alphabetical order
        Collections.sort(allMovies);
//        Array adapter which contains all movies (with checkboxes)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allMovies);
//        Set the array adapter into the list view
        movieList.setAdapter(arrayAdapter);
//        To make it allow to check checkboxes(multiple choice)
        movieList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        Actions to be done once the list view clicked
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) adapterView.getItemAtPosition(i);
                if(movieList.isItemChecked(i)) {
//                    To add into the favourite movie list when checked
                    favMovies.add(selected);
                } else {
//                    To remove from favourite movie list when unchecked
                    favMovies.remove(selected);
                }
            }
        });
    }

//    When click on the add to favourite button
    public void addToFavourite(View view) {
//        Calling the favourite movie list
        for (int i=0 ; i<favMovies.size() ; i++) {
            SQLiteDatabase db = movie.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("rFav", "Favourite");
//            Update the favourite movies on database
            db.update("registerMovie", values, "rTitle" + "=" + "'" + favMovies.get(i) + "'", null);
        }
//        Toast message
        Toast toast = Toast.makeText(getApplicationContext(), "Added to Favourite List", Toast.LENGTH_SHORT);
        toast.show();
    }
}