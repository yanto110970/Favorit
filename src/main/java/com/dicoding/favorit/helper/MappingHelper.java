package com.dicoding.favorit.helper;

import android.database.Cursor;

import com.dicoding.favorit.db.DatabaseContract;
import com.dicoding.favorit.entity.Favorit;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Favorit> mapCursorToArrayList(Cursor FavoritsCursor) {
        ArrayList<Favorit> favoritList = new ArrayList<>();

        while (FavoritsCursor.moveToNext()) {
            int id = FavoritsCursor.getInt(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns._ID));
            String userid = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERID));
            String username = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERNAME));
            String date = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.DATE));
            String avatar = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.AVATAR));
            String userurl = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERURL));
            favoritList.add(new Favorit(id, userid, username, date, avatar, userurl));
        }

        return favoritList;
    }

    public static Favorit mapCursorToObject(Cursor FavoritsCursor) {
        FavoritsCursor.moveToFirst();
        int id = FavoritsCursor.getInt(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns._ID));
        String userid = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERID));
        String username = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERNAME));
        String date = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.DATE));
        String avatar = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.AVATAR));
        String userurl = FavoritsCursor.getString(FavoritsCursor.getColumnIndexOrThrow(DatabaseContract.FavorColumns.USERURL));
        return new Favorit(id, userid, username, date, avatar, userurl);
    }
}

