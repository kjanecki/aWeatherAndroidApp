package com.marcinkaminski.aWeather.utilities;

import android.content.Context;
import android.util.Log;

import com.marcinkaminski.aWeather.R;
import com.marcinkaminski.aWeather.data.AppPreferences;


public final class AppWeatherUtils {
    private static final String LOG_TAG = AppWeatherUtils.class.getSimpleName();

//    Converts temperature in celcius to Fahrenheit scale
    private static double celsiusToFahrenheit(double temperatureInCelsius) {
        return (temperatureInCelsius * 1.8) + 32;
    }

//    Formats the temperature to temperature chosen by user
    public static String formatTemperature(Context context, double temperature) {
        if (!AppPreferences.isMetric(context)) {
            temperature = celsiusToFahrenheit(temperature);
        }
        int temperatureFormatResourceId = R.string.format_temperature;
        return String.format(context.getString(temperatureFormatResourceId), temperature);
    }

//    Returns properly formatted high and low temperature based on user's prefferences etc.
    public static String formatHighLows(Context context, double high, double low) {
        return formatTemperature(context, Math.round(high)) + " / " + formatTemperature(context, Math.round(low));
    }

//    Returns properly formatted wind based on user's prefferences
    public static String getFormattedWind(Context context, float windSpeed) {
        int windFormat = R.string.format_wind_kmh;
        if (!AppPreferences.isMetric(context)) {
            windFormat = R.string.format_wind_mph;
            windSpeed = .621371192237334f * windSpeed;
        }
        return String.format(context.getString(windFormat), windSpeed);
    }

//    Returns the description of the weather condition based on weather ID
    public static String getStringForWeatherCondition(Context context, int weatherId) {
        int stringId;
        switch (weatherId) {
            case 1:
                stringId = R.string.condition_1;
                break;
            case 2:
                stringId = R.string.condition_2;
                break;
            case 3:
                stringId = R.string.condition_3;
                break;
            case 4:
                stringId = R.string.condition_4;
                break;
            case 5:
                stringId = R.string.condition_5;
                break;
            case 6:
                stringId = R.string.condition_6;
                break;
            case 7:
                stringId = R.string.condition_7;
                break;
            case 10:
                stringId = R.string.condition_10;
                break;
            case 20:
                stringId = R.string.condition_20;
                break;
            case 30:
                stringId = R.string.condition_30;
                break;
            case 40:
                stringId = R.string.condition_40;
                break;
            case 50:
                stringId = R.string.condition_50;
                break;
            case 60:
                stringId = R.string.condition_60;
                break;
            case 70:
                stringId = R.string.condition_70;
                break;
            case 11:
                stringId = R.string.condition_11;
                break;
            case 12:
                stringId = R.string.condition_12;
                break;
            case 13:
                stringId = R.string.condition_13;
                break;
            case 14:
                stringId = R.string.condition_14;
                break;
            case 15:
                stringId = R.string.condition_15;
                break;
            case 16:
                stringId = R.string.condition_16;
                break;
            case 17:
                stringId = R.string.condition_17;
                break;
            case 21:
                stringId = R.string.condition_21;
                break;
            case 22:
                stringId = R.string.condition_22;
                break;
            case 23:
                stringId = R.string.condition_23;
                break;
            case 24:
                stringId = R.string.condition_24;
                break;
            case 25:
                stringId = R.string.condition_25;
                break;
            case 26:
                stringId = R.string.condition_26;
                break;
            case 27:
                stringId = R.string.condition_27;
                break;
            case 31:
                stringId = R.string.condition_31;
                break;
            case 32:
                stringId = R.string.condition_32;
                break;
            case 33:
                stringId = R.string.condition_33;
                break;
            case 34:
                stringId = R.string.condition_34;
                break;
            case 35:
                stringId = R.string.condition_35;
                break;
            case 36:
                stringId = R.string.condition_36;
                break;
            case 37:
                stringId = R.string.condition_37;
                break;
            case 41:
                stringId = R.string.condition_41;
                break;
            case 42:
                stringId = R.string.condition_42;
                break;
            case 43:
                stringId = R.string.condition_43;
                break;
            case 44:
                stringId = R.string.condition_44;
                break;
            case 45:
                stringId = R.string.condition_45;
                break;
            case 46:
                stringId = R.string.condition_46;
                break;
            case 47:
                stringId = R.string.condition_47;
                break;
            case 51:
                stringId = R.string.condition_51;
                break;
            case 52:
                stringId = R.string.condition_52;
                break;
            case 53:
                stringId = R.string.condition_53;
                break;
            case 54:
                stringId = R.string.condition_54;
                break;
            case 55:
                stringId = R.string.condition_55;
                break;
            case 56:
                stringId = R.string.condition_56;
                break;
            case 57:
                stringId = R.string.condition_57;
                break;
            case 61:
                stringId = R.string.condition_61;
                break;
            case 62:
                stringId = R.string.condition_62;
                break;
            case 63:
                stringId = R.string.condition_63;
                break;
            case 64:
                stringId = R.string.condition_64;
                break;
            case 65:
                stringId = R.string.condition_65;
                break;
            case 66:
                stringId = R.string.condition_66;
                break;
            case 67:
                stringId = R.string.condition_67;
                break;
            case 71:
                stringId = R.string.condition_71;
                break;
            case 72:
                stringId = R.string.condition_72;
                break;
            case 73:
                stringId = R.string.condition_73;
                break;
            case 74:
                stringId = R.string.condition_74;
                break;
            case 75:
                stringId = R.string.condition_75;
                break;
            case 76:
                stringId = R.string.condition_76;
                break;
            case 77:
                stringId = R.string.condition_77;
                break;
            default:
                return context.getString(R.string.condition_unknown, weatherId);
        }

        return context.getString(stringId);
    }

//    Returns resource path for weather id
    public static int getIconResourceIdForWeatherCondition(int weatherId) {
        if(weatherId == 1){
            return R.drawable.ic_sun;
        }
        if(weatherId == 2){
            return R.drawable.ic_cloud;
        }
        else if(weatherId == 3){
            return R.drawable.ic_sun_cloud;
        }
        else if(weatherId == 4){
            return R.drawable.ic_storm;
        }
        else if(weatherId == 5){
            return R.drawable.ic_sun_storm;
        }
        else if(weatherId == 6){
            return R.drawable.ic_storm;
        }
        else if(weatherId == 7){
            return R.drawable.ic_sun_storm;
        }
        else if(weatherId == 10){
            return R.drawable.ic_rain;
        }
        else if(weatherId == 20){
            return R.drawable.ic_snow;
        }
        else if(weatherId == 30){
            return R.drawable.ic_rain_snow;
        }
        else if(weatherId == 40){
//            We don't have fog so we put clouds in here
            return R.drawable.ic_cloud;
        }
        else if(weatherId == 50){
            return R.drawable.ic_rain;
        }
        else if(weatherId == 60){
            return R.drawable.ic_snow;
        }
        else if(weatherId == 70){
            return R.drawable.ic_rain_snow;
        }
        else if(weatherId == 11){
            return R.drawable.ic_sun_rain;
        }
        else if(weatherId == 12){
            return R.drawable.ic_rain;
        }
        else if(weatherId == 13){
            return R.drawable.ic_sun_rain;
        }
        else if(weatherId == 14){
            return R.drawable.ic_storm_rain;
        }
        else if(weatherId == 15){
            return R.drawable.ic_sun_storm_rain;
        }
        else if(weatherId == 16){
            return R.drawable.ic_storm_rain;
        }
        else if(weatherId == 17){
            return R.drawable.ic_sun_storm_rain;
        }
        else if(weatherId == 21){
            return R.drawable.ic_sun_snow;
        }
        else if(weatherId == 22){
            return R.drawable.ic_snow;
        }
        else if(weatherId == 23){
            return R.drawable.ic_sun_snow;
        }
        else if(weatherId == 24){
            return R.drawable.ic_storm_snow;
        }
        else if(weatherId == 25){
            return R.drawable.ic_sun_storm_snow;
        }
        else if(weatherId == 26){
            return R.drawable.ic_storm_snow;
        }
        else if(weatherId == 27){
            return R.drawable.ic_sun_storm_snow;
        }
        else if(weatherId == 31){
            return R.drawable.ic_sun_rain_snow;
        }
        else if(weatherId == 32){
            return R.drawable.ic_rain_snow;
        }
        else if(weatherId == 33){
            return R.drawable.ic_sun_rain_snow;
        }
        else if(weatherId == 34){
            return R.drawable.ic_storm_rain_snow;
        }
        else if(weatherId == 35){
            return R.drawable.ic_all;
        }
        else if(weatherId == 36){
            return R.drawable.ic_storm_rain_snow;
        }
        else if(weatherId == 37){
            return R.drawable.ic_all;
        }
        else if(weatherId == 41){
            return R.drawable.ic_sun_cloud;
        }
        else if(weatherId == 42){
            return R.drawable.ic_cloud;
        }
        else if(weatherId == 43){
            return R.drawable.ic_sun_cloud;
        }
        else if(weatherId == 44){
            return R.drawable.ic_storm;
        }
        else if(weatherId == 45){
            return R.drawable.ic_sun_storm;
        }
        else if(weatherId == 46){
            return R.drawable.ic_storm;
        }
        else if(weatherId == 47){
            return R.drawable.ic_sun_storm;
        }
        else if(weatherId == 51){
            return R.drawable.ic_sun_rain;
        }
        else if(weatherId == 52){
            return R.drawable.ic_rain;
        }
        else if(weatherId == 53){
            return R.drawable.ic_sun_rain;
        }
        else if(weatherId == 54){
            return R.drawable.ic_storm_rain;
        }
        else if(weatherId == 55){
            return R.drawable.ic_sun_storm_rain;
        }
        else if(weatherId == 56){
            return R.drawable.ic_storm_rain;
        }
        else if(weatherId == 57){
            return R.drawable.ic_sun_storm_rain;
        }
        else if(weatherId == 61){
            return R.drawable.ic_sun_snow;
        }
        else if(weatherId == 62){
            return R.drawable.ic_snow;
        }
        else if(weatherId == 63){
            return R.drawable.ic_sun_snow;
        }
        else if(weatherId == 64){
            return R.drawable.ic_storm_snow;
        }
        else if(weatherId == 65){
            return R.drawable.ic_sun_storm_snow;
        }
        else if(weatherId == 66){
            return R.drawable.ic_storm_snow;
        }
        else if(weatherId == 67){
            return R.drawable.ic_sun_storm_snow;
        }
        else if(weatherId == 71){
            return R.drawable.ic_sun_rain_snow;
        }
        else if(weatherId == 72){
            return R.drawable.ic_rain_snow;
        }
        else if(weatherId == 73){
            return R.drawable.ic_sun_rain_snow;
        }
        else if(weatherId == 74){
            return R.drawable.ic_storm_rain_snow;
        }
        else if(weatherId == 75){
            return R.drawable.ic_all;
        }
        else if(weatherId == 76){
            return R.drawable.ic_storm_rain_snow;
        }
        else if(weatherId == 77){
            return R.drawable.ic_all;
        }

        Log.e(LOG_TAG, "Unknown Weather Code: " + weatherId);
        return R.drawable.ic_all;
    }

}