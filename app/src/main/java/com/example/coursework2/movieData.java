package com.example.coursework2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class movieData extends SQLiteOpenHelper {

    public static String[] FROM = { _ID, "rTitle", "rYear", "rDirector", "rActorsActresses", "rRating", "rReview", "rFav"};

//    To create the database movies.db
    public movieData(Context context) {
        super(context, "movies.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        To create the registerMovie table and add columns
        sqLiteDatabase.execSQL("CREATE TABLE registerMovie (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "rTitle TEXT NOT NULL, " +
                "rYear INTEGER, " +
                "rDirector TEXT, " +
                "rActorsActresses TEXT, " +
                "rRating INTEGER, " +
                "rReview TEXT, " +
                "rFav TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        to drop the table on an upgrade
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS registerMovie;");
        onCreate(sqLiteDatabase);
    }
}
