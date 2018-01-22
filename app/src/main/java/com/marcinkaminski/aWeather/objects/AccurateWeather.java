package com.marcinkaminski.aWeather.objects;

import com.marcinkaminski.aWeather.utilities.IconManagerUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AccurateWeather {
    private Weather accurateWeather;
    private AccurateWeather(Weather weather){
        this.accurateWeather = weather;
    }

    public Weather getWeather() {
        return accurateWeather;
    }

    @Override
    public String toString() {
        return "AccurateWeatherUtils{" +
                "accurateWeather=" + accurateWeather +
                '}';
    }

    public static class Builder{
        private ArrayList<Weather> weatherList;
        private Date date;
        private ArrayList<String> summary;
        private String icon = "";
        private double temperatureMin=0;
        private double temperatureMax=0;
        private Double windSpeed=0.0;
        private Double humidity=0.0;
        private Double pressure=0.0;

        public static Builder builder(){
            return new Builder();
        }

        private Builder(){
            this.weatherList = new ArrayList<Weather>();
        }
        public Builder setWeatherList(ArrayList<Weather> weatherList) {
            this.weatherList = weatherList;
            return this;
        }

        public Builder addWeather(Weather weather){
            this.weatherList.add(weather);
            return this;
        }

        public AccurateWeather build() throws IllegalAccessException {
            if (weatherList.size() != 0) {
                int minTempDivader = 0;
                int maxTempDivader = 0;
                int windSpeedDivader = 0;
                int humidityDivader = 0;
                int pressureDivader = 0;
                int[] conditions = {0,0,0,0,0,0,0};
                summary = new ArrayList<>();

                for(Weather weather : weatherList){
                    if(weather!= null) {
                        try {
                            uploadDate(weather.getDate());
                            temperatureMin += weather.getTemperatureMin();
                            ++minTempDivader;
                            temperatureMax += weather.getTemperatureMax();
                            ++maxTempDivader;
                            summary.add(weather.getOneLineSummary());
                            IconManagerUtils.decode(weather.getIcon(),conditions);

                            Double humid = weather.getHumidity();
                            if (humid != null) {
                                this.humidity += humid;
                                ++humidityDivader;
                            }
                            Double press = weather.getPressure();
                            if (press != null) {
                                this.pressure += press;
                                ++pressureDivader;
                            }
                            Double wind = weather.getWindSpeed();
                            if (wind != null) {
                                this.windSpeed += wind;
                                ++windSpeedDivader;
                            }

                        } catch (UnsupportedOperationException e) {
                            e.printStackTrace();
                        }
                    }
                }

                this.temperatureMax/=maxTempDivader;
                this.temperatureMin/=minTempDivader;
                this.windSpeed/=windSpeedDivader;
                this.humidity/=humidityDivader;
                this.pressure/=pressureDivader;
                this.icon = Integer.toString(IconManagerUtils.encode(conditions));


                return new AccurateWeather(new Weather(this.date,this.summary,this.icon,
                        this.temperatureMax,this.temperatureMin,this.windSpeed,this.humidity,this.pressure));
            } else {
                throw new IllegalAccessException("The weather list are empty. Can't build the AccurateWeather.");
            }
        }

        private void uploadDate(Date date){

            if(this.date == null){
                this.date = date;
            }
            else {
                Calendar cal = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();

                cal.setTime(this.date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                cal2.setTime(date);
                int year2 = cal2.get(Calendar.YEAR);
                int month2 = cal2.get(Calendar.MONTH);
                int day2 = cal2.get(Calendar.DAY_OF_MONTH);


                if (year!=year2 || month!=month2 || day!=day2){
//                    throw new UnsupportedOperationException("Date missmatch");
                }
            }
        }
    }
}
