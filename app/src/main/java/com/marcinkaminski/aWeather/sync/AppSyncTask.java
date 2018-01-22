package com.marcinkaminski.aWeather.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.text.format.DateUtils;
import com.marcinkaminski.aWeather.data.AppPreferences;
import com.marcinkaminski.aWeather.data.WeatherContract;
import com.marcinkaminski.aWeather.objects.Forecast;
import com.marcinkaminski.aWeather.utilities.NotificationUtils;

//    It syncs data from API services with our application. It will be synced after every 3h (set in AppSyncUtils)
public class AppSyncTask {
    synchronized public static void syncWeather(Context context) {
        try {
            ContentValues[] weatherValues = Forecast.getFullForecast(context);

            if (weatherValues != null && weatherValues.length != 0) {
                ContentResolver appContentResolver = context.getContentResolver();

//                delete old data
                appContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

//                insert new data
                appContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);

//                Checks when the last notification was sent
                boolean notificationsEnabled = AppPreferences.areNotificationsEnabled(context);

//                Counts the time from last notification
                long timeSinceLastNotification = AppPreferences.getEllapsedTimeSinceLastNotification(context);

                boolean oneDayPassedSinceLastNotification = false;
                if (timeSinceLastNotification >= DateUtils.DAY_IN_MILLIS) {
                    oneDayPassedSinceLastNotification = true;
                }

//                If user has enabled notifications and one day passed from last notification
//                we notify user about new, updated weather
                if (notificationsEnabled && oneDayPassedSinceLastNotification) {
                    NotificationUtils.notifyUserOfNewWeather(context);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}