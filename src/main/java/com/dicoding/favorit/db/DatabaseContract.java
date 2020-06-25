package com.dicoding.favorit.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.dicoding.favorit";
    private static final String SCHEME = "content";

    private DatabaseContract(){}
    public static final class FavorColumns implements BaseColumns {
        public static final String TABLE_NAME = "favorit";

        public static final String USERID = "userid";
        public static final String USERNAME = "username";
        public static final String DATE = "date";
        public static final String AVATAR = "avatar";
        public static final String USERURL = "userurl";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

}

