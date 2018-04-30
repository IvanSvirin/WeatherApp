package com.example.isvirin.weatherapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.isvirin.weatherapp.data.cache.DataBaseSQLite;

import static com.example.isvirin.weatherapp.data.cache.DataBaseSQLite.DB_TABLE;

public class DBContentProvider extends ContentProvider {
    private DataBaseSQLite dataBaseSQLite;
    private UriMatcher uriMatcher;
    public static final String AUTH = "com.example.isvirin.weatherapp.data.DBContentProvider";
    public static final int CODE_LIST = 1;
    public static final int CODE_ITEM = 2;

    @Override
    public boolean onCreate() {
        dataBaseSQLite = new DataBaseSQLite(getContext());
        uriMatcher = new UriMatcher(0);
        uriMatcher.addURI(AUTH, "items", CODE_LIST);
        uriMatcher.addURI(AUTH, "items/#", CODE_ITEM);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        dataBaseSQLite.open();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_LIST:
                cursor = dataBaseSQLite.getDatabase().query(DB_TABLE, null, null, null, null, null, null);
                return cursor;
            case CODE_ITEM:
                cursor = dataBaseSQLite.getDatabase().query(DB_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        dataBaseSQLite.open();
        dataBaseSQLite.getDatabase().insert(DB_TABLE, null, values);
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
