package com.example.isvirin.weatherapp.data.model;

public class Location {
    private String name;
    private String country;

    public Location(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Location() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
