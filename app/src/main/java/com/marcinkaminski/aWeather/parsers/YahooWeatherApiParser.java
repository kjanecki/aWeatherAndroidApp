package com.marcinkaminski.aWeather.parsers;

import com.marcinkaminski.aWeather.objects.Forecast;
import com.marcinkaminski.aWeather.utilities.JsonReaderUtils;
import com.marcinkaminski.aWeather.objects.Weather;
import com.marcinkaminski.aWeather.utilities.WeatherApiParseableUtils;
import com.marcinkaminski.aWeather.objects.City;
import com.marcinkaminski.aWeather.objects.LocationPoints;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class YahooWeatherApiParser implements WeatherApiParseableUtils {

    @Override
    public Forecast parse(City city) throws IOException, JSONException {

        LocationPoints<Double,Double> coords = city.getCoordinates();
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+city.getName()+ "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        JSONObject apiData = JsonReaderUtils.readJsonFromUrl(url)
                .getJSONObject("query")
                .getJSONObject("results")
                .getJSONObject("channel")
                .getJSONObject("item");

        int forecastRange = 6;

        Forecast forecast = new Forecast();

        JSONArray dailyData = apiData.getJSONArray("forecast");

        for(int i=0;i<forecastRange;++i){
            JSONObject dayData = dailyData.getJSONObject(i);
            String date = dayData.getString("date");
            DateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
            Date parsedDate = null;
            try {
                parsedDate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            forecast.addWeather(new Weather(parsedDate,
                    dayData.getString("text")+ " ~Yahoo!",
                    adaptWeatherCondition(dayData.getString("code")),
                    (dayData.getDouble("high")-32)*5/9,
                    (dayData.getDouble("low")-32)*5/9,
                    null,
                    null,
                    null));
        }
        return forecast;
    }

    @Override
    public String adaptWeatherCondition(String condition) {

        int code = Integer.parseInt(condition);

        if(code >= 31 && code <= 34){
            return "1";
        }
        else if(code==29 || code==30 || code==43){
            return "3";
        }
        else if(code==26 || code==27 || code==28){
            return "2";
        }
        else if((code>=5 && code<=12) || code==35 || code==39){
            return "10";
        }
        else if((code>=13 && code<=18) || (code>=40 && code<=42) || code==45){
            return "20";
        }
        else if(code==1 || code==3 || code==4 || code==37 ||
                code==38 || code==44 || code==46){
            return "4";
        }
        else if((code>=19 && code<=22)){
            return "40";
        }
        else if(code==0 || code==2 || code==23 || code==24 ||
                code==25 || code==36){
            return "0";
        }

        return null;
    }
}
