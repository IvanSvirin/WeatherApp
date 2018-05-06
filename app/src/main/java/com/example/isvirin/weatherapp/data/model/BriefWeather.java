package com.example.isvirin.weatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class BriefWeather {
    @SerializedName("temp")
    private double temp;
    @SerializedName("pressure")
    private long pressure;
    @SerializedName("humidity")
    private long humidity;
    @SerializedName("temp_min")
    private long tempMin;
    @SerializedName("temp_max")
    private long tempMax;

    public BriefWeather(double temp, long pressure, long humidity, long tempMin, long tempMax) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

    public BriefWeather() {

    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public long getTempMin() {
        return tempMin;
    }

    public void setTempMin(long tempMin) {
        this.tempMin = tempMin;
    }

    public long getTempMax() {
        return tempMax;
    }

    public void setTempMax(long tempMax) {
        this.tempMax = tempMax;
    }
}
