package com.example.coursework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditSelectedMovie extends AppCompatActivity {

    private EditText selectedTitle;
    private EditText selectedYear;
    private EditText selectedDirector;
    private EditText selectedActors;
    private TextView selectedRating;
    private EditText selectedReview;
    private EditText selectedFavourite;
    private ImageView coloredStars;
    private movieData movie;
    private int numberOfColoredStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_selected_movie);

//        Call the views of xml file
        selectedTitle = findViewById(R.id.editSelectedTitle);
        selectedYear = findViewById(R.id.editSelectedYear);
        selectedDirector = findViewById(R.id.editSelectedDirector);
        selectedActors = findViewById(R.id.editSelectedActors);
        selectedReview = findViewById(R.id.editSelectedReview);
        selectedFavourite = findViewById(R.id.editSelectedFavourite);

//        Set the values taken from the parent activity
        selectedTitle.setText(String.valueOf(EditMovie.title));
        selectedYear.setText(String.valueOf(EditMovie.year));
        selectedDirector.setText(String.valueOf(EditMovie.director));
        selectedActors.setText(String.valueOf(EditMovie.actors));
        selectedReview.setText(String.valueOf(EditMovie.review));
        selectedFavourite.setText(String.valueOf(EditMovie.favourite));
        numberOfColoredStars = EditMovie.rating;

//        A loop to call the 10 stars
        for (int i=1 ; i<=10 ; i++) {
//            Call stars from the id
            int colored = getResources().getIdentifier("imageStar" + i , "id", "com.example.coursework2");
            coloredStars = findViewById(colored);
            if (i<=numberOfColoredStars) {
//                Color stars yellow if it's position is less than or equal to the rating
                coloredStars.setColorFilter(getResources().getColor(R.color.yellowStar));
            } else {
//                Color stars white if it's position is greater than the rating
                coloredStars.setColorFilter(getResources().getColor(R.color.white));
            }
        }
//        To connect with the sqlite database
        movie = new movieData(this);
    }

//    When click on the update button
    public void updateMovies(View view) {
        SQLiteDatabase db = movie.getWritableDatabase();
        ContentValues values = new ContentValues();
//        To put edited data
        values.put("rTitle", selectedTitle.getText().toString());
        values.put("rYear", selectedYear.getText().toString());
        values.put("rDirector", selectedDirector.getText().toString());
        values.put("rActorsActresses", selectedActors.getText().toString());
        values.put("rReview", selectedReview.getText().toString());
        values.put("rRating", numberOfColoredStars);
        values.put("rFav", selectedFavourite.getText().toString());
//        To update data according to the given movie id
        db.update("registerMovie", values, "_id" + "=" + String.valueOf(EditMovie.id+1) , null);

//        Successful toast message
        Toast toast = Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT);
        toast.show();
    }

//    When click on the 1st star
    public void star1Clicked(View view) {
        numberOfColoredStars = 1;
        checkColoredStars();
    }

//    When click on the 2nd star
    public void star2Clicked(View view) {
        numberOfColoredStars = 2;
        checkColoredStars();
    }

//    When click on the 3rd star
    public void star3Clicked(View view) {
        numberOfColoredStars = 3;
        checkColoredStars();
    }

//    When click on the 4th star
    public void star4Clicked(View view) {
        numberOfColoredStars = 4;
        checkColoredStars();
    }


//    When click on the 5th star
    public void star5Clicked(View view) {
        numberOfColoredStars = 5;
        checkColoredStars();
    }

//    When click on the 6th star
    public void star6Clicked(View view) {
        numberOfColoredStars = 6;
        checkColoredStars();
    }

//    When click on the 7th star
    public void star7Clicked(View view) {
        numberOfColoredStars = 7;
        checkColoredStars();
    }

//    When click on the 8th star
    public void star8Clicked(View view) {
        numberOfColoredStars = 8;
        checkColoredStars();
    }

//    When click on the 9th star
    public void star9Clicked(View view) {
        numberOfColoredStars = 9;
        checkColoredStars();
    }

//    When click on the 10th star
    public void star10Clicked(View view) {
        numberOfColoredStars = 10;
        checkColoredStars();
    }

//    To change the colors of stars
    private void checkColoredStars() {
        for (int i=1 ; i<=10 ; i++) {
            int colored = getResources().getIdentifier("imageStar" + i , "id", "com.example.coursework2");
            coloredStars = findViewById(colored);
//            Color stars yellow if it's position is less than or equal to the edited rating
            if (i<=numberOfColoredStars) {
                coloredStars.setColorFilter(getResources().getColor(R.color.yellowStar));
            } else {
//                Color stars white if it's position is greater than the edited rating
                coloredStars.setColorFilter(getResources().getColor(R.color.white));
            }
        }
    }
}