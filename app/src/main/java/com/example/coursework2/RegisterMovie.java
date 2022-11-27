package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.BaseColumns._ID;

public class RegisterMovie extends AppCompatActivity {

    private EditText title;
    private EditText year;
    private EditText director;
    private EditText actorsActresses;
    private EditText rating;
    private EditText review;
    private movieData movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

//        Call the views of xml file
        title = findViewById(R.id.editRegisterTitle);
        year = findViewById(R.id.editRegisterYear);
        director = findViewById(R.id.editRegisterDirector);
        actorsActresses = findViewById(R.id.editRegisterActors);
        rating = findViewById(R.id.editRegisterRating);
        review = findViewById(R.id.editRegisterReview);

//        To connect with the sqlite database
        movie = new movieData(this);
    }

//    When click on the save button
    public void saveRegisteredMovie(View view) {
//        If any field is empty
        if ((title.getText().toString().equals("")) || (year.getText().toString().equals("")) || (director.getText().toString().equals("")) ||
                (actorsActresses.getText().toString().equals("")) || (rating.getText().toString().equals("")) || (review.getText().toString().equals(""))) {
//            Toast message if fields are empty
            Toast toast = Toast.makeText(getApplicationContext(), "Fill all fields please", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

//        If the year is wrong
        if (Integer.parseInt(year.getText().toString()) < 1895) {
//            Toast message if the year is less than 1895
            Toast toast = Toast.makeText(getApplicationContext(), "Year should be greater than 1895", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

//        If rating is wrong
        if (Integer.parseInt(rating.getText().toString()) < 1 || Integer.parseInt(rating.getText().toString()) > 10) {
//            Toast message if rating is not between 1 to 10
            Toast toast = Toast.makeText(getApplicationContext(), "Enter a rating between 1 to 10", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

//        To save movie data on the database
        saveMovie(title.getText().toString(), Integer.parseInt(year.getText().toString()), director.getText().toString(), actorsActresses.getText().toString(),
                Integer.parseInt(rating.getText().toString()), review.getText().toString());
    }

//    To save movie data on the database
    private void saveMovie(String title, int year, String director, String actors, int rating, String review) {
        SQLiteDatabase db = movie.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rTitle", title);
        values.put("rYear", year);
        values.put("rDirector", director);
        values.put("rActorsActresses", actors);
        values.put("rRating", rating);
        values.put("rReview", review);
        values.put("rFav", "Not Favourite");
//        To insert data inside edit texts to the database
        db.insertOrThrow("registerMovie", null, values);

//        Successful toast message
        Toast toast = Toast.makeText(getApplicationContext(), "Movie Registered successfully!", Toast.LENGTH_SHORT);
        toast.show();
    }
}