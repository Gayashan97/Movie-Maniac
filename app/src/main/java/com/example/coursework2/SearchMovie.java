package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchMovie extends AppCompatActivity {

    private EditText search;
    private ListView searchList;
    private movieData movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

//        Call the views of xml file
        search = findViewById(R.id.editSearch);
        searchList = findViewById(R.id.listSearchedMovies);

//        To connect with the sqlite database
        movie = new movieData(this);
    }

//    When click on the look up button
    public void lookupMovie(View view) {
        List<String> searchedMovies = new ArrayList<String>();
//        Gets all data from the movies database
        SQLiteDatabase db = movie.getReadableDatabase();
//        Sql query to select all related data(LIKE to make case insensitive)
        Cursor cursor = db.rawQuery("SELECT * FROM registerMovie WHERE rTitle LIKE '%"+ search.getText() +"%' " +
                "OR rDirector LIKE '%" + search.getText() +"%' OR rActorsActresses LIKE '%" + search.getText() + "%';", null);
        if (cursor.moveToFirst()) {
            for (int j = 0; j < cursor.getCount(); j++) {
//                To add all movie titles into the searchedMovies list
                searchedMovies.add(cursor.getString(1));
                cursor.moveToNext();
            }
        }
        cursor.close();
//        Array adapter which contains all searchedMovies
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchedMovies);
//        Set the array adapter into the list view
        searchList.setAdapter(arrayAdapter);
//        Actions to be done once the list view clicked
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }
}