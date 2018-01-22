package com.marcinkaminski.aWeather.utilities;

import com.marcinkaminski.aWeather.objects.City;
import com.marcinkaminski.aWeather.objects.Forecast;

import org.json.JSONException;

import java.io.IOException;

public interface WeatherApiParseableUtils {
    Forecast parse(City city) throws IOException, JSONException;

    String adaptWeatherCondition(String condition);
}
