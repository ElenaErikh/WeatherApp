package com.example.weatherapp;

import java.io.Serializable;

public class WeatherContainer implements Serializable {
    public int position = 0;
    public String cityName = "";
    public String temperature = "+25";
    public String wind = "";
    public String pressure = "";
}
