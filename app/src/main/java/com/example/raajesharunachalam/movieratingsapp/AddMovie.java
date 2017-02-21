package com.example.raajesharunachalam.movieratingsapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsContract;
import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsDBHelper;

public class AddMovie extends AppCompatActivity {

    EditText title;
    EditText description;
    EditText rating;
    Button submit;
    private MovieRatingsDBHelper dbHelper;
    private String genre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        Log.v("AddMovie", "On Create is called");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = (EditText) findViewById(R.id.enter_title);
        description = (EditText) findViewById(R.id.enter_description);
        rating = (EditText) findViewById(R.id.enter_rating);
        submit = (Button) findViewById(R.id.submit_button);
        dbHelper = new MovieRatingsDBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        rating.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stringRating = rating.getText().toString();
                Log.v("Rating", stringRating);

                if(!(stringRating.equals(""))){
                    int numRating = Integer.parseInt(stringRating);
                    if (numRating < 0 || numRating > 5) {
                        rating.getText().clear();
                        Toast.makeText(getBaseContext(), "You entered a value less than 0 or greater than 5." +
                                " Please try again.", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();

                String stringTitle = title.getText().toString();
                cv.put(MovieRatingsContract.MovieRatingsEntry.TITLE, stringTitle);

                cv.put(MovieRatingsContract.MovieRatingsEntry.GENRE, genre);

                String stringDescription = description.getText().toString();
                cv.put(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION, stringDescription);

                String stringRating = rating.getText().toString();
                int intRating = Integer.parseInt(stringRating);
                cv.put(MovieRatingsContract.MovieRatingsEntry.RATING, intRating);

                db.insert(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, cv);
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void chooseGenre(View view){
        RadioButton radioButton = (RadioButton) view;
        boolean isChecked = radioButton.isChecked();
        int id = view.getId();
        if(isChecked){
            switch(id){
                case R.id.action:
                    genre = "Action";
                    break;
                case R.id.adventure:
                    genre = "Adventure";
                    break;
                case R.id.comedy:
                    genre = "Comedy";
                    break;
                case R.id.drama:
                    genre = "Drama";
                    break;
                case R.id.horror:
                    genre = "Horror";
                    break;
                case R.id.romance:
                    genre = "Romance";
                    break;
                case R.id.sci_fi:
                    genre = "Sci-Fi";
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Add Movie", "On Destroy Called");
    }
}
