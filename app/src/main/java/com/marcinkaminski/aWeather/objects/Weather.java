package com.marcinkaminski.aWeather.objects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by marcinkaminski on 19/09/2017.
 */
public class Weather {
    private Date date;
    private ArrayList<String> summary;
    private String icon = "";
    private Double temperatureMax;
    private Double temperatureMin;
    private Double windSpeed;
    private Double humidity;
    private Double pressure;

    public Weather(Date date, String summary, String icon, double temperatureMax, double temperatureMin, Double windSpeed, Double humidity, Double pressure) {
        this(date,icon,temperatureMax,temperatureMin,windSpeed,humidity,pressure);
        this.summary=new ArrayList<>();
        this.summary.add(summary);
    }

    public Weather(Date date, ArrayList<String> summary, String icon, double temperatureMax, double temperatureMin, Double windSpeed, Double humidity, Double pressure) {
        this(date,icon,temperatureMax,temperatureMin,windSpeed,humidity,pressure);
        this.summary = summary;
    }

    private Weather(Date date, String icon, double temperatureMax, double temperatureMin, Double windSpeed, Double humidity, Double pressure) {
        this.date = date;
        this.icon = icon;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date=" + date +
                ", summary=" + summary +
                ", icon='" + icon + '\'' +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", windSpeed=" + windSpeed +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}'+"\n";
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<String> getSummary() {
        return summary;
    }

    public Date getDate() {
        return date;
    }

    public Double getPressure() {
        return pressure;
    }

    public String getOneLineSummary() {
        return summary.get(0);
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getHumidity() {
        return humidity;
    }

    public double getRealFealTemperatureMax(){
        return countRealFealTemperature(temperatureMax);
    }

    public double getRealFealTemperatureMin(){
        return countRealFealTemperature(temperatureMin);
    }

    private double countRealFealTemperature(double temperature){
        return (37-((37- temperature)/(0.68-0.0014*humidity+(1/(1.76+1.4*Math.pow((windSpeed/3.6), 0.75)))))-0.29*temperature*(1-(humidity/100))) * 100;
    }
}
