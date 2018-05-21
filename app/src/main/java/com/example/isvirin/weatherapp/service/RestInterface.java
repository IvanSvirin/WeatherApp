package com.example.isvirin.weatherapp.service;

import com.example.isvirin.weatherapp.data.model.currentweather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("weather")
    Call<CurrentWeather> getWeatherReport(@Query("q") String city, @Query("appid") String appId);

    @GET("forecast")
    Call<Object> getForecast(@Query("q") String city, @Query("appid") String appId);
}
