package com.example.isvirin.weatherapp.data.cache;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private Context context;
    public static final String USER_LOCATION = "user_location";


    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void saveString(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
