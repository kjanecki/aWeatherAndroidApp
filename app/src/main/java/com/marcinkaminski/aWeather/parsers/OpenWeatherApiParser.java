package com.marcinkaminski.aWeather.parsers;

import com.marcinkaminski.aWeather.data.AppPreferences;
import com.marcinkaminski.aWeather.objects.Weather;
import com.marcinkaminski.aWeather.objects.Forecast;
import com.marcinkaminski.aWeather.utilities.IconManagerUtils;
import com.marcinkaminski.aWeather.utilities.JsonReaderUtils;
import com.marcinkaminski.aWeather.utilities.WeatherApiParseableUtils;
import com.marcinkaminski.aWeather.objects.City;
import com.marcinkaminski.aWeather.objects.LocationPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class OpenWeatherApiParser implements WeatherApiParseableUtils {

    @Override
    public Forecast parse(City city) throws IOException, JSONException {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+city.getName()+"&units=metric&mode=json&&APPID=fdafeb9bb1af50757dd842cd1ec381a4";

        JSONObject apiData = JsonReaderUtils.readJsonFromUrl(url);

        JSONObject cityData = apiData.getJSONObject("city").getJSONObject("coord");
        double lat = cityData.getDouble("lat");
        double lon = cityData.getDouble("lon");
        city.setCoordinates(lat,lon);
        Forecast forecast = new Forecast();

        JSONArray daysList = apiData.getJSONArray("list");

        forecast.addWeather(null);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date currentDate = new Date();

        int k = 1;
        while(dateFormat.format(new Date(daysList.getJSONObject(k).getLong("dt")*1000)).equals(dateFormat.format(currentDate))){
            ++k;
        }

        for(int i=k; i<daysList.length();i+=8){
            JSONObject actualWeather = daysList.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
            Date date = new Date(daysList.getJSONObject(i).getLong("dt")*1000);
            String summary = actualWeather.getString("description")+" ~OpenWeather";
            //String icon = adaptWeatherCondition(actualWeather.getString("icon"));
            Double temperatureMax=daysList.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
            Double temperatureMin=daysList.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
            Double windSpeedMax=daysList.getJSONObject(i).getJSONObject("wind").getDouble("speed");
            Double humidityMax=daysList.getJSONObject(i).getJSONObject("main").getDouble("humidity");
            int[] conditions = {0,0,0,0,0,0,0};

            double pressure = 0.0;

            for( int j=i; j<i+8 && j <daysList.length();++j){

                actualWeather = daysList.getJSONObject(j).getJSONArray("weather").getJSONObject(0);
                JSONObject dayData = daysList.getJSONObject(j);
                JSONObject temp = dayData.getJSONObject("main");
                Double tmax = temp.getDouble("temp_max");
                Double tmin = temp.getDouble("temp_min");
                Double wind = dayData.getJSONObject("wind").getDouble("speed");
                Double hum = temp.getDouble("humidity");

                IconManagerUtils.decode(adaptWeatherCondition(actualWeather.getString("icon")),conditions);

                if(tmax > temperatureMax){
                    temperatureMax=tmax;
                }

                if(tmin < temperatureMin){
                    temperatureMin=tmin;
                }

                if(wind > windSpeedMax){
                    windSpeedMax = wind;
                }

                if(hum > humidityMax){
                    humidityMax = hum;
                }

                pressure+=temp.getDouble("pressure");
            }

            pressure/=8;

            String icon = Integer.toString(IconManagerUtils.encode(conditions));

            forecast.addWeather(new Weather(date,summary,
                    icon,temperatureMax,temperatureMin,
                    windSpeedMax,
                    humidityMax,
                    pressure));

        }
        return forecast;
    }

    @Override
    public String adaptWeatherCondition(String condition) {
        switch (condition){
            case "01d":
            case "01n":
                return "1";

            case "02d":
            case "02n":
                return "3";

            case "03d":
            case "03n":
            case "04d":
            case "04n":
                return "2";

            case "09d":
            case "09n":
            case "10d":
            case "10n":
                return "10";

            case "11d":
            case "11n":
                return "4";

            case "13d":
            case "13n":
                return "20";

            case "50d":
            case "50n":
                return "40";

        }

        return "0";
    }
}
