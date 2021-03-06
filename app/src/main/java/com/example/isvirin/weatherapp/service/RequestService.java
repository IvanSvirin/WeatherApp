package com.example.isvirin.weatherapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.isvirin.weatherapp.data.cache.SharedPreferencesManager;
import com.example.isvirin.weatherapp.view.activity.MainActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.isvirin.weatherapp.data.cache.SharedPreferencesManager.USER_LOCATION;

public class RequestService extends IntentService {
    private String url1 = "http://openweathermap.org/data/2.5/weather?q=";
    private String url2 = "&appid=b6907d289e10d714a6e88b30761fae22";
    public static final String WEATHER = "weather";
    public static final String WEATHER_REQUEST = "weather_request";

    public RequestService() {
        super("name");
    }

    public RequestService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferencesManager preferencesManager = new SharedPreferencesManager(this);
        String city = preferencesManager.getString(USER_LOCATION);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url1 + city + url2)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Intent intent1 = new Intent();
            intent1.putExtra(WEATHER, response.body().string());
            intent1.setAction(WEATHER_REQUEST);
            sendBroadcast(intent1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
