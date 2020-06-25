package com.dicoding.favorit.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.dicoding.favorit.db.FavoritHelper;

import java.util.Objects;

import static com.dicoding.favorit.db.DatabaseContract.AUTHORITY;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.CONTENT_URI;
import static com.dicoding.favorit.db.DatabaseContract.FavorColumns.TABLE_NAME;

public class FavoritProvider extends ContentProvider {
    private static final int FAVOR = 1;
    private static final int FAVOR_ID = 2;
    private static final int FAVOR_USER = 3;

    private FavoritHelper favoritHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVOR);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", FAVOR_ID);

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME + "/userid/*", FAVOR_USER);
    }

    @Override
    public boolean onCreate() {
        favoritHelper = FavoritHelper.getInstance(getContext());
        favoritHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Log.i("pesan", Integer.toString(sUriMatcher.match(uri)));
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FAVOR:
                cursor = favoritHelper.queryAll();
                break;
            case FAVOR_ID:
                cursor = favoritHelper.queryById(uri.getLastPathSegment());
                break;
            case FAVOR_USER:
                Log.i("pesan", Objects.requireNonNull(uri.getLastPathSegment()));
                cursor = favoritHelper.queryByUserId(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case FAVOR:
                added = favoritHelper.insert(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FAVOR_ID:
                updated = favoritHelper.update(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVOR_ID:
                deleted = favoritHelper.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, null);

        return deleted;
    }

}