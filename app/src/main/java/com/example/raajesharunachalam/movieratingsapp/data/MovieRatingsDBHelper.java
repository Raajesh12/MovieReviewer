package com.example.raajesharunachalam.movieratingsapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raajesharunachalam on 1/5/17.
 */

public class MovieRatingsDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Movie Ratings Database";
    public static final int DATABASE_VERSION = 1;

    public MovieRatingsDBHelper(Context context){
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATABASE = "CREATE TABLE " + MovieRatingsContract.MovieRatingsEntry.TABLE_NAME + " (" +
                MovieRatingsContract.MovieRatingsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MovieRatingsContract.MovieRatingsEntry.TITLE + " TEXT NOT NULL,"
                + MovieRatingsContract.MovieRatingsEntry.GENRE + " TEXT NOT NULL,"
                + MovieRatingsContract.MovieRatingsEntry.RATING + " INTEGER NOT NULL,"
                + MovieRatingsContract.MovieRatingsEntry.DESCRIPTION + " TEXT NOT NULL);";
        db.execSQL(CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieRatingsContract.MovieRatingsEntry.TABLE_NAME);
        onCreate(db);
    }
}
