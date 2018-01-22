package com.marcinkaminski.aWeather.parsers;

import com.marcinkaminski.aWeather.objects.Weather;
import com.marcinkaminski.aWeather.objects.Forecast;
import com.marcinkaminski.aWeather.utilities.JsonReaderUtils;
import com.marcinkaminski.aWeather.utilities.WeatherApiParseableUtils;
import com.marcinkaminski.aWeather.objects.City;
import com.marcinkaminski.aWeather.objects.LocationPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class DarkSkyApiParser implements WeatherApiParseableUtils {
    @Override
    public Forecast parse(City city) throws IOException, JSONException {
        LocationPoints<Double,Double> coords = city.getCoordinates();
        String url = "https://api.darksky.net/forecast/36dc88a988e734ae0d4333b43eecf53f/"+coords.getLatitude()+","+coords.getLongitude()+"?units=ca&exclude=minutely&exclude=flags";

        JSONObject apiData = JsonReaderUtils.readJsonFromUrl(url);

        Forecast forecast = new Forecast();
        int foracastRange = 6;

        JSONArray dailyData = apiData.getJSONObject("daily").getJSONArray("data");

        for(int i=0; i<foracastRange;++i){
            JSONObject dayData = dailyData.getJSONObject(i);

            forecast.addWeather(new Weather(new Date(dayData.getLong("time")*1000),
                    dayData.getString("summary")+" ~DarkSky",
                    adaptWeatherCondition(dayData.getString("icon")),
                    dayData.getDouble("temperatureMax"),
                    dayData.getDouble("temperatureMin"),
                    dayData.getDouble("windSpeed"),
                    dayData.getDouble("humidity")*100,
                    dayData.getDouble("pressure")));
        }

        return forecast;
    }

    @Override
    public String adaptWeatherCondition(String condition) {
        switch (condition){
            case "clear-day":
            case "clear-night":
                return "1";

            case "partly-cloudy-day":
            case "partly-cloudy-night":
                return "3";

            case "cloudy":
                return "2";

            case "rain":
                return "10";

            case "snow":
            case"sleet":
                return "20";

            case "fog":
                return "40";

            case "wind":
                return "0";
        }
        return "0";
    }
}
