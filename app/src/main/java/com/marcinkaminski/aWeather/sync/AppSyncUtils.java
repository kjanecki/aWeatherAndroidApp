package com.marcinkaminski.aWeather.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.marcinkaminski.aWeather.data.WeatherContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class AppSyncUtils {
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static boolean sInitialized;
    private static final String AWEATHER_SYNC_TAG = "aweather-sync";

//    Provides an API for scheduling units of work (represented by JobService subclasses) that will
// be executed in your app's proces, uses the scheduling engine inside Google Play services
// to provide a backwards compatible JobScheduler
    static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job syncWeatherJob = dispatcher.newJobBuilder()
                .setService(AppJobService.class)
                .setTag(AWEATHER_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncWeatherJob);
    }

//  Starts syncing if content is empty. Used in MainActivity.
    synchronized public static void initialize(@NonNull final Context context) {
        if (sInitialized){ return; }
        sInitialized = true;
        scheduleFirebaseJobDispatcherSync(context);
        Thread checkForEmptyContent = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;
                String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
                String selectionStatement = WeatherContract.WeatherEntry.getSqlSelectForTodayOnwards();

                Cursor cursor = context.getContentResolver().query(
                        forecastQueryUri,
                        projectionColumns,
                        selectionStatement,
                        null,
                        null);

                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }
                cursor.close();
            }
        });
        checkForEmptyContent.start();
    }

//    Starts the syncing immediately.
    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, AppSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

}