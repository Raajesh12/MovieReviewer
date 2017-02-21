package com.example.raajesharunachalam.movieratingsapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsContract;
import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsDBHelper;

import static android.R.attr.button;

public class EditMovie extends AppCompatActivity {

    EditText title;
    RadioButton action;
    RadioButton adventure;
    RadioButton comedy;
    RadioButton drama;
    RadioButton horror;
    RadioButton romance;
    RadioButton sciFi;
    EditText description;
    EditText rating;
    MovieRatingsDBHelper dbHelper;
    Button submitEdited;

    String updatedTitle;
    String updatedGenre;
    String updatedDescription;
    int updatedRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        Log.v("EditMovie", "On Create is called");

        Intent intentThatCameToThisActivity = getIntent();
        if(intentThatCameToThisActivity.getStringExtra(Intent.EXTRA_TEXT).equals("edit")) {

            title = (EditText) findViewById(R.id.edit_enter_title);
            action = (RadioButton) findViewById(R.id.edit_action);
            adventure = (RadioButton) findViewById(R.id.edit_adventure);
            comedy = (RadioButton) findViewById(R.id.edit_comedy);
            drama = (RadioButton) findViewById(R.id.edit_drama);
            horror = (RadioButton) findViewById(R.id.edit_horror);
            romance = (RadioButton) findViewById(R.id.edit_romance);
            sciFi = (RadioButton) findViewById(R.id.edit_sci_fi);
            description = (EditText) findViewById(R.id.edit_enter_description);
            rating = (EditText) findViewById(R.id.edit_enter_rating);
            submitEdited = (Button) findViewById(R.id.edit_submit_button);

            dbHelper = new MovieRatingsDBHelper(this);
            final SQLiteDatabase db = dbHelper.getReadableDatabase();
            final int idOfItemSwiped = intentThatCameToThisActivity.getIntExtra("id", -1);

            new AsyncTask<Void, Void, Cursor>(){

                @Override
                protected Cursor doInBackground(Void... params) {
                    String where = MovieRatingsContract.MovieRatingsEntry._ID + " = ?";
                    String stringIdOfItemSwiped = String.valueOf(idOfItemSwiped);
                    String[] whereArgs = {stringIdOfItemSwiped};
                    Cursor cursor = db.query(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, where, whereArgs, null, null, null);
                    return cursor;
                }

                @Override
                protected void onPostExecute(Cursor cursor) {
                    cursor.moveToFirst();

                    String stringTitle = cursor.getString(cursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.TITLE));
                    title.setText(stringTitle);

                    String genreLowercase = cursor.getString(cursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.GENRE)).toLowerCase();
                    Log.v("Genre: ", genreLowercase);

                    if(genreLowercase.equals("action")){
                        action.setChecked(true);
                    }

                    else if(genreLowercase.equals("adventure")){
                        adventure.setChecked(true);
                    }

                    else if(genreLowercase.equals("comedy")){
                        comedy.setChecked(true);
                    }

                    else if(genreLowercase.equals("horror")){
                        horror.setChecked(true);
                    }
                    else if(genreLowercase.equals("drama")){
                        drama.setChecked(true);
                    }

                    else if(genreLowercase.equals("romance")){
                            romance.setChecked(true);}

                    else if(genreLowercase.equals("sci-fi")){
                            sciFi.setChecked(true);
                    }

                    String stringDescription = cursor.getString(cursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION));
                    description.setText(stringDescription);

                    int intRating = cursor.getInt(cursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.RATING));
                    String stringRating = String.valueOf(intRating);
                    rating.setText(stringRating);
                }
            }.execute();

            submitEdited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues updatedEntry = new ContentValues();

                    updatedTitle = title.getText().toString();
                    updatedEntry.put(MovieRatingsContract.MovieRatingsEntry.TITLE, updatedTitle);

                    if(action.isChecked()){
                        updatedGenre = "Action";
                    }
                    else if(adventure.isChecked()){
                        updatedGenre = "Adventure";
                    }
                    else if(comedy.isChecked()){
                        updatedGenre = "Comedy";
                    }
                    else if(drama.isChecked()){
                        updatedGenre = "Drama";
                    }
                    else if(horror.isChecked()){
                        updatedGenre = "Horror";
                    }
                    else if(romance.isChecked()){
                        updatedGenre = "Romance";
                    }
                    else if(romance.isChecked()){
                        updatedGenre = "Sci-Fi";
                    }
                    updatedEntry.put(MovieRatingsContract.MovieRatingsEntry.GENRE, updatedGenre);

                    updatedDescription = description.getText().toString();
                    updatedEntry.put(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION, updatedDescription);

                    String stringUpdatedRating = rating.getText().toString();
                    updatedRating = Integer.parseInt(stringUpdatedRating);
                    updatedEntry.put(MovieRatingsContract.MovieRatingsEntry.RATING, updatedRating);

                    final ContentValues updatedEntry2 = updatedEntry;

                    final String where = MovieRatingsContract.MovieRatingsEntry._ID + " = ?";
                    String stringIdOfItemSwiped = String.valueOf(idOfItemSwiped);
                    final String[] whereArgs = {stringIdOfItemSwiped};

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            db.update(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, updatedEntry2, where, whereArgs);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            onBackPressed();
                        }
                    }.execute();
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Edit Movie", "On Destroy Called");
    }

    /*public void chooseUpdatedGenre(View view){
        RadioButton radioButton = (RadioButton) view;
        boolean isChecked = radioButton.isChecked();
        int id = view.getId();
        if(isChecked){
            switch(id){
                case R.id.edit_action:
                    updatedGenre = "Action";
                    break;
                case R.id.edit_adventure:
                    updatedGenre = "Adventure";
                    break;
                case R.id.edit_comedy:
                    updatedGenre = "Comedy";
                    break;
                case R.id.edit_horror:
                    updatedGenre = "Horror";
                    break;
                case R.id.edit_romance:
                    updatedGenre = "Romance";
                    break;
                case R.id.edit_sci_fi:
                    updatedGenre = "Sci-Fi";
                    break;
            }
        }
    }*/
}
