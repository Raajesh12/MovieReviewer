package com.example.raajesharunachalam.movieratingsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsContract;
import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsDBHelper;

public class MainActivity extends AppCompatActivity {

    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_GENRE = 2;
    public static final int COLUMN_RATING = 3;
    public static final int COLUMN_DESCRIPTION = 4;
    private ImageView plusButton;
    private MovieRatingsDBHelper dbHelper;
    private MovieListAdapter mAdapter;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private Button clearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.sharedPreferencesFile), Context.MODE_PRIVATE);
        Log.v("Preferences Contains", String.valueOf(sharedPreferences.contains(getString(R.string.colorPreference))));
        Log.v("Color Preference", sharedPreferences.getString(getString(R.string.colorPreference), ""));

        Log.v("MainActivity", "On Create is called");

        dbHelper = new MovieRatingsDBHelper(this);



        plusButton = (ImageView) findViewById(R.id.plus);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddMovie = new Intent(MainActivity.this, AddMovie.class);
                startActivity(toAddMovie);
            }
        });



        /*SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null);
        ContentValues values = new ContentValues();
        values.put(MovieRatingsContract.MovieRatingsEntry.TITLE, "Rush Hour");
        values.put(MovieRatingsContract.MovieRatingsEntry.GENRE, "Comedy");
        values.put(MovieRatingsContract.MovieRatingsEntry.RATING, 5);
        values.put(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION, "Great movie that highlights the best of Jackie Chan and Chris Tucker as they partner together with the LAPD.");
        db.insert(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, values);

        ContentValues values2 = new ContentValues();
        values2.put(MovieRatingsContract.MovieRatingsEntry.TITLE, "Dark Knight");
        values2.put(MovieRatingsContract.MovieRatingsEntry.GENRE, "Action");
        values2.put(MovieRatingsContract.MovieRatingsEntry.RATING, 3.5);
        values2.put(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION, "Great action packed movie with excellent acting by the former Joker. A great update to this classic series.");
        db.insert(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(MovieRatingsContract.MovieRatingsEntry.TITLE, "Don Jon");
        values3.put(MovieRatingsContract.MovieRatingsEntry.GENRE, "Romantic");
        values3.put(MovieRatingsContract.MovieRatingsEntry.RATING, 1);
        values3.put(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION, "Horrible film that shows indecency and lack of respect for audience. Terrible to watch with family or friends. Decent idea, but bad screenplay, acting, and carry through.");
        db.insert(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, values3);*/

        /*SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null);*/

        mRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_list);

        mAdapter = new MovieListAdapter(this, null);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Intent toEditActivity = new Intent(MainActivity.this, EditMovie.class);
                int idNumber = viewHolder.itemView.getId();
                toEditActivity.putExtra("id", idNumber);
                toEditActivity.putExtra(Intent.EXTRA_TEXT, "edit");
                startActivity(toEditActivity);

            }
        }).attachToRecyclerView(mRecyclerView);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int id = viewHolder.itemView.getId();

                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                        String where = MovieRatingsContract.MovieRatingsEntry._ID + " = ?";
                        String stringId = String.valueOf(id);
                        String[] whereArgs = {stringId};
                        db2.delete(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, where, whereArgs);
                        return null;
                    }
                }.execute();

                new AsyncTask<Void, Void, Cursor>(){

                    @Override
                    protected Cursor doInBackground(Void... params) {
                        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                        Cursor cursor = db2.query(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null, null, null, null, null);
                        return cursor;
                    }

                    @Override
                    protected void onPostExecute(Cursor cursor) {
                        mAdapter.swapCursor(cursor);
                    }
                }.execute();

            }
        }).attachToRecyclerView(mRecyclerView);




        new AsyncTask<Void, Void, Cursor>(){

            @Override
            protected Cursor doInBackground(Void... params) {
                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                String sortOrder = MovieRatingsContract.MovieRatingsEntry.RATING + " DESC";
                mCursor = db2.query(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
                return mCursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                mAdapter.swapCursor(cursor);
            }
        }.execute();

        clearAll = (Button) findViewById(R.id.clear_all);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>(){

                    @Override
                    protected Void doInBackground(Void... params) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        mAdapter.swapCursor(null);
                    }
                }.execute();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesFile), Context.MODE_PRIVATE);
        String color = sharedPreferences.getString(getString(R.string.colorPreference), "");

        if(color.equals("blue")){
            clearAll.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        else if(color.equals("yellow")){
            clearAll.setBackgroundColor(ContextCompat.getColor(this, R.color.darkYellow));
        }
        else if(color.equals("green")){
            clearAll.setBackgroundColor(ContextCompat.getColor(this, R.color.darkGreen));
        }

        mAdapter = new MovieListAdapter(this, mCursor);
        mRecyclerView.setAdapter(mAdapter);
        Log.v("Main Activity", "On start is called");
        new AsyncTask<Void, Void, Cursor>(){

            @Override
            protected Cursor doInBackground(Void... params) {
                SQLiteDatabase db2 = dbHelper.getReadableDatabase();
                String sortOrder = MovieRatingsContract.MovieRatingsEntry.RATING + " DESC";
                mCursor = db2.query(MovieRatingsContract.MovieRatingsEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
                return mCursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                mAdapter.swapCursor(cursor);
            }
        }.execute();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_activity_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings_item){
            Intent toSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(toSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
