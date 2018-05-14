package com.example.isvirin.weatherapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class WeatherApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
