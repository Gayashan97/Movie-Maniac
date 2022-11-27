package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class FavouriteMovie extends AppCompatActivity {

    private movieData movie;
    private ListView favMovieList;
    private List<String> allFavMovies = new ArrayList<String>();
    private List<String> updatedNotFavMovies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movie);

//        List view of favourite movies
        favMovieList = findViewById(R.id.listFavouriteMovies);

//        To connect with the sqlite database
        movie = new movieData(this);

        checkFavouriteMovies();
    }

//    to check favourite movies
    private void checkFavouriteMovies() {
//        Gets all data from the movies database
        SQLiteDatabase db = movie.getReadableDatabase();
        Cursor cursor = db.query("registerMovie", movie.FROM, null, null, null, null, null);
        while (cursor.moveToNext()) {
            if(cursor.getString(7).equals("Favourite")) {
//                Add favourite movie titles into the allFavMovies list
                allFavMovies.add(cursor.getString(1));
            }
        }
        cursor.close();
//        Array adapter which contains all favourite movies (with checkboxes)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, allFavMovies);
//        Set the array adapter into the list view
        favMovieList.setAdapter(arrayAdapter);
//        To make it allow to check checkboxes(multiple choice)
        favMovieList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        for (int i=0 ; i<allFavMovies.size() ; i++) {
//            To make all checkboxes checked
            favMovieList.setItemChecked(i, true);
        }
//        Actions to be done once the list view clicked
        favMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) adapterView.getItemAtPosition(i);
                if(!favMovieList.isItemChecked(i)) {
//                    Add into the notFavList if the movie is unchecked
                    updatedNotFavMovies.add(selected);
                } else {
//                    Remove from notFavList if the movie is checked again
                    updatedNotFavMovies.remove(selected);
                }
            }
        });
    }

//    When click on the save button
    public void updateFavMovies(View view) {
//        To update rFav column
        for (int i=0 ; i<updatedNotFavMovies.size() ; i++) {
            SQLiteDatabase db = movie.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("rFav", "Not Favourite");
//            To update rFav if unchecked the given move title
            db.update("registerMovie", values, "rTitle" + "=" + "'" + updatedNotFavMovies.get(i) + "'", null);
        }
//        Successful toast message
        Toast toast = Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_SHORT);
        toast.show();
//        Make allFavMovies list empty after saved
        allFavMovies = new ArrayList<String>();
        checkFavouriteMovies();
    }
}