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

public class EditMovie extends AppCompatActivity {

    private movieData movie;
    private ListView movieList;
    private List<String> allMovies = new ArrayList<String>();
    static int year = 0;
    static String title = null;
    static String director = null;
    static String actors = null;
    static String review = null;
    static int rating = 0;
    static String favourite = null;
    static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

//        List view of movies
        movieList = findViewById(R.id.listEditMovies);

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
//        Array adapter which contains all movies
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allMovies);
//        Set the array adapter into the list view
        movieList.setAdapter(arrayAdapter);
//        Actions to be done once the list view clicked
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = (String) adapterView.getItemAtPosition(i);

//                Query to select the clicked movie details
                Cursor cursor2 = db.rawQuery("SELECT * FROM registerMovie WHERE rTitle='"+ selected +"'", null);
//                Collect all details to pass to the child activity
                if (cursor2.moveToFirst()) {
                    for (int j = 0; j < cursor2.getCount(); j++) {
                        title = cursor2.getString(1);
                        year = cursor2.getInt(2);
                        director = cursor2.getString(3);
                        actors = cursor2.getString(4);
                        rating = cursor2.getInt(5);
                        review = cursor2.getString(6);
                        favourite = cursor2.getString(7);
                        cursor2.moveToNext();
                    }
                }

//                Id to pass to the child activity
                id = i;
//                Call to start the child activity
                Intent intent = new Intent(EditMovie.this, EditSelectedMovie.class);
                startActivity(intent);
            }
        });
    }
}