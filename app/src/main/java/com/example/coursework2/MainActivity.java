package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    When click on register button
    public void registerMovie(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterMovie.class);
        startActivity(intent);
    }

//    When click on display button
    public void displayMovies(View view) {
        Intent intent = new Intent(MainActivity.this, DisplayMovie.class);
        startActivity(intent);
    }

//    When click on favourite button
    public void displayFavouriteMovies(View view) {
        Intent intent = new Intent(MainActivity.this, FavouriteMovie.class);
        startActivity(intent);
    }

//    When click on edit button
    public void editMovies(View view) {
        Intent intent = new Intent(MainActivity.this, EditMovie.class);
        startActivity(intent);
    }

//    When click on search button
    public void searchMovies(View view) {
        Intent intent = new Intent(MainActivity.this, SearchMovie.class);
        startActivity(intent);
    }

//    When click on ratings button
    public void movieRatings(View view) {
        Intent intent = new Intent(MainActivity.this, Ratings.class);
        startActivity(intent);
    }
}