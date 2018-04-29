package com.example.isvirin.weatherapp.data.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.isvirin.weatherapp.data.model.Location;

import java.util.Collections;
import java.util.List;

public class DataBaseSQLite {
    public static final String DB_NAME = "our_db";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "our_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";

    public static final String DB_CREATE = "create table " + DB_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text," +
            COLUMN_COUNTRY + " text" +
            ");";

    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DataBaseSQLite(Context context) {
        this.context = context;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public void open() {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void write(Location location){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, location.getName());
        cv.put(COLUMN_COUNTRY, location.getCountry());
        database.insert(DB_TABLE, null, cv);
    }

    public List<Location> get() {
        Cursor cursor = database.query(DB_TABLE, null, null, null, null, null, null);
        List<Location> locations = Collections.emptyList();
        cursor.moveToFirst();
        Location location = new Location();
        while(cursor.moveToNext()) {
            String s = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String f = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
            location.setName(s);
            location.setCountry(f);
            locations.add(location);
        }
        cursor.close();
        return locations;
    }
}
