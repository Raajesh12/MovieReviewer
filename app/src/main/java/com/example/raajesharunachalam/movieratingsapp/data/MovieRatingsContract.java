package com.example.raajesharunachalam.movieratingsapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raajesharunachalam on 1/5/17.
 */

public class MovieRatingsContract {

    public static final String CONTENT_AUTHORITY = "com.example.raajesharunachalam.movieratingsapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH = "ratings";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

    public static final class MovieRatingsEntry implements BaseColumns{
        public static final String TABLE_NAME = "Ratings";
        public static final String TITLE = "title";
        public static final String GENRE = "genre";
        public static final String RATING = "rating";
        public static final String DESCRIPTION = "description";
    }
}
