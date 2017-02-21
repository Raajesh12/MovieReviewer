package com.example.raajesharunachalam.movieratingsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raajesharunachalam.movieratingsapp.data.MovieRatingsContract;

/**
 * Created by raajesharunachalam on 1/5/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieAdapterViewHoler> {

    private String mColor;
    private String mTextSize;
    private Context mContext;
    private Cursor mCursor;
    public MovieListAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
        if(mCursor != null){mCursor.moveToFirst();}
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.sharedPreferencesFile), Context.MODE_PRIVATE);
        mColor = sharedPreferences.getString(mContext.getString(R.string.colorPreference), "");
        mTextSize = sharedPreferences.getString(mContext.getString(R.string.fontSizePreference), "");
    }

    @Override
    public MovieAdapterViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View list_item_view = inflater.inflate(R.layout.movie_list_item, parent, false);
        if(mColor.equals("blue")){
            list_item_view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
        else if(mColor.equals("yellow")){
            list_item_view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.yellow));
        }
        else if(mColor.equals("green")){
            list_item_view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.green));
        }
        return new MovieAdapterViewHoler(list_item_view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHoler holder, int position) {
        if(mCursor == null){
            return;
        }
        if(mCursor.moveToPosition(position) == false){
            return;
        }

        String title = mCursor.getString(mCursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.TITLE));
        holder.mTitle.setText(title);

        String genre = mCursor.getString(mCursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.GENRE));
        holder.mGenre.setText(genre);

        int rating = mCursor.getInt(mCursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.RATING));
        holder.mRating.setText(Integer.toString(rating));

        if(rating <= 2){
            holder.mIcon.setImageResource(R.mipmap.red_frown_face);
        }
        else if(rating < 4){
            holder.mIcon.setImageResource(R.mipmap.yellow_medium_face);
        }
        else{
            holder.mIcon.setImageResource(R.mipmap.green_smiley_face);
        }

        String description = mCursor.getString(mCursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry.DESCRIPTION));
        holder.mDescription.setText(description);

        int id = mCursor.getInt(mCursor.getColumnIndex(MovieRatingsContract.MovieRatingsEntry._ID));
        holder.itemView.setId(id);

        if(mTextSize.equals("small")){
            //Do nothing since default is small
        }
        else if(mTextSize.equals("medium")){
            holder.mTitle.setTextSize(25);
            holder.mGenre.setTextSize(17);
            holder.mRating.setTextSize(40);
            holder.mDescription.setTextSize(19);
        }

        else{
            holder.mTitle.setTextSize(30);
            holder.mGenre.setTextSize(22);
            holder.mRating.setTextSize(45);
            holder.mDescription.setTextSize(24);
        }

    }

    @Override
    public int getItemCount() {
        if(this.mCursor == null){return 0;}
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        this.mCursor = newCursor;
        notifyDataSetChanged();
    }



    public class MovieAdapterViewHoler extends RecyclerView.ViewHolder{
        public ImageView mIcon;
        public TextView mTitle;
        public TextView mGenre;
        public TextView mRating;
        public TextView mDescription;
        public MovieAdapterViewHoler(View view){
            super(view);
            mIcon = (ImageView) view.findViewById(R.id.smiley_face_image);
            mTitle = (TextView) view.findViewById(R.id.movie_title);
            mGenre = (TextView) view.findViewById(R.id.movie_genre);
            mRating = (TextView) view.findViewById(R.id.rating);
            mDescription = (TextView) view.findViewById(R.id.description);
        }

    }
}
