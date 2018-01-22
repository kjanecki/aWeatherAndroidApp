package com.marcinkaminski.aWeather.objects;

import android.content.ContentValues;
import android.content.Context;
import com.marcinkaminski.aWeather.data.AppPreferences;
import com.marcinkaminski.aWeather.data.WeatherContract;
import com.marcinkaminski.aWeather.parsers.DarkSkyApiParser;
import com.marcinkaminski.aWeather.parsers.OpenWeatherApiParser;
import com.marcinkaminski.aWeather.parsers.YahooWeatherApiParser;
import com.marcinkaminski.aWeather.utilities.AppDateUtils;
import com.marcinkaminski.aWeather.utilities.WeatherApiParseableUtils;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by marcinkaminski on 19/09/2017.
 */
public class Forecast {
    public static String TAG = Forecast.class.getSimpleName();
    private ArrayList<Weather> dailyWeather;

    public Forecast() {
        dailyWeather = new ArrayList<>();
    }

    public void addWeather(Weather weather) {
        dailyWeather.add(weather);
    }

    public static Forecast getForecastFromApi(City city, WeatherApiParseableUtils parser) {
        try {
            return parser.parse(city);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "dailyWeather=" + dailyWeather +
                '}';
    }

    public Weather getWeather(int i) {
        if (i < dailyWeather.size()) {
            return dailyWeather.get(i);
        } else {
            return null;
        }
    }

    public int getSize() {
        return dailyWeather.size();
    }

    public static ContentValues[] getFullForecast(Context context) {
        City city = new City(AppPreferences.getPreferredWeatherLocation(context), 0, 0);

        ArrayList<Forecast> forecastList = new ArrayList<>();
        forecastList.add(Forecast.getForecastFromApi(city,
                new OpenWeatherApiParser()));

        AppPreferences.setLocationDetails(context,city.getCoordinates().getLatitude(),city.getCoordinates().getLongitude());

        forecastList.add(Forecast.getForecastFromApi(city,
                new DarkSkyApiParser()));

        forecastList.add(Forecast.getForecastFromApi(city,
                new YahooWeatherApiParser()));

        return getContentValuesArray(forecastList);
    }

    private static ContentValues[] getContentValuesArray(ArrayList<Forecast> forecastList) {
        ContentValues[] contentValuesArray = new ContentValues[forecastList.get(0).getSize()];

        for (int i = 0; i < contentValuesArray.length; ++i) {
            AccurateWeather.Builder accurateWeatherBuilder = AccurateWeather.Builder.builder();


            for (Forecast forecast : forecastList) {
                accurateWeatherBuilder.addWeather(forecast.getWeather(i));
            }

            try {
                AccurateWeather accurateWeather = accurateWeatherBuilder.build();
                Weather weather = accurateWeather.getWeather();

                long normalizedUtcStartDay = AppDateUtils.getNormalizedUtcDateForToday();
                long dateTimeMillis = normalizedUtcStartDay + AppDateUtils.DAY_IN_MILLIS * i;
                ContentValues weatherValues = new ContentValues();
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_DATE, dateTimeMillis);
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_HUMIDITY, weather.getHumidity());
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_PRESSURE, weather.getPressure());
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WIND_SPEED, weather.getWindSpeed());
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MAX_TEMP, weather.getTemperatureMax());
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_MIN_TEMP, weather.getTemperatureMin());
                weatherValues.put(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID, weather.getIcon());
                contentValuesArray[i] = weatherValues;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return contentValuesArray;
    }
}
