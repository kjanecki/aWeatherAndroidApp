package com.marcinkaminski.aWeather;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.marcinkaminski.aWeather.data.WeatherContract;
import com.marcinkaminski.aWeather.databinding.ActivityDetailedWeatherBinding;
import com.marcinkaminski.aWeather.utilities.AppDateUtils;
import com.marcinkaminski.aWeather.utilities.AppWeatherUtils;

public class DetailedWeatherActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String FORECAST_SHARE_HASHTAG = " #aWeatherApp";

    public static final String[] WEATHER_DETAIL_PROJECTION = {
            WeatherContract.WeatherEntry.COLUMN_DATE,
            WeatherContract.WeatherEntry.COLUMN_MAX_TEMP,
            WeatherContract.WeatherEntry.COLUMN_MIN_TEMP,
            WeatherContract.WeatherEntry.COLUMN_HUMIDITY,
            WeatherContract.WeatherEntry.COLUMN_PRESSURE,
            WeatherContract.WeatherEntry.COLUMN_WIND_SPEED,
            WeatherContract.WeatherEntry.COLUMN_WEATHER_ID
    };

    public static final int INDEX_WEATHER_DATE = 0;
    public static final int INDEX_WEATHER_MAX_TEMP = 1;
    public static final int INDEX_WEATHER_MIN_TEMP = 2;
    public static final int INDEX_WEATHER_HUMIDITY = 3;
    public static final int INDEX_WEATHER_PRESSURE = 4;
    public static final int INDEX_WEATHER_WIND_SPEED = 5;
    public static final int INDEX_WEATHER_CONDITION_ID = 6;
    private static final int ID_DETAILED_LOADER = 2;
    private String mForecastSummary;
    private Uri mUri;
    private ActivityDetailedWeatherBinding mDetailedWeatherBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailedWeatherBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_weather);
        mUri = getIntent().getData();
        if (mUri == null){
            throw new NullPointerException("URI for DetailedWeatherActivity cannot be null");
        }

        getSupportLoaderManager().initLoader(ID_DETAILED_LOADER, null, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_share) {
            Intent shareIntent = createShareForecastIntent();
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecastSummary + FORECAST_SHARE_HASHTAG)
                .getIntent();
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        return shareIntent;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {
        switch (loaderId) {
            case ID_DETAILED_LOADER:
                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            return;
        }

        /********
         * Icon *
         *******/
        int weatherId = data.getInt(INDEX_WEATHER_CONDITION_ID);
        int weatherImageId = AppWeatherUtils.getIconResourceIdForWeatherCondition(weatherId);
        mDetailedWeatherBinding.primaryInfo.weatherIcon.setImageResource(weatherImageId);

        /********
         * Date *
         *******/
        long localDateMidnightGmt = data.getLong(INDEX_WEATHER_DATE);
        String dateText = AppDateUtils.getFriendlyDateString(this, localDateMidnightGmt, true);
        mDetailedWeatherBinding.primaryInfo.date.setText(dateText);

        /******************
         * Max temperature *
         ******************/
        double highInCelsius = data.getDouble(INDEX_WEATHER_MAX_TEMP);
        String highString = AppWeatherUtils.formatTemperature(this, highInCelsius);
        String highA11y = getString(R.string.a11y_high_temp, highString);
        mDetailedWeatherBinding.primaryInfo.highTemperature.setText(highString);
        mDetailedWeatherBinding.primaryInfo.highTemperature.setContentDescription(highA11y);

        /*******************
         * Min temperature *
         ******************/
        double lowInCelsius = data.getDouble(INDEX_WEATHER_MIN_TEMP);
        String lowString = AppWeatherUtils.formatTemperature(this, lowInCelsius);
        String lowA11y = getString(R.string.a11y_low_temp, lowString);
        mDetailedWeatherBinding.primaryInfo.lowTemperature.setText(lowString);
        mDetailedWeatherBinding.primaryInfo.lowTemperature.setContentDescription(lowA11y);

        /***************
         * Description *
         **************/
        String description = AppWeatherUtils.getStringForWeatherCondition(this, weatherId);
        String descriptionA11y = getString(R.string.a11y_forecast, description);
        mDetailedWeatherBinding.primaryInfo.weatherDescription.setText(description);
        mDetailedWeatherBinding.primaryInfo.weatherDescription.setContentDescription(descriptionA11y);
        mDetailedWeatherBinding.primaryInfo.weatherIcon.setContentDescription(descriptionA11y);

        /************
         * Humidity *
         ************/
        float humidity = data.getFloat(INDEX_WEATHER_HUMIDITY);
        String humidityString = getString(R.string.format_humidity, humidity);
        String humidityA11y = getString(R.string.a11y_humidity, humidityString);
        mDetailedWeatherBinding.extraDetails.humidity.setText(humidityString);
        mDetailedWeatherBinding.extraDetails.humidity.setContentDescription(humidityA11y);
        mDetailedWeatherBinding.extraDetails.humidityLabel.setContentDescription(humidityA11y);

        /************
         * Pressure *
         ************/
        float pressure = data.getFloat(INDEX_WEATHER_PRESSURE);
        String pressureString = getString(R.string.format_pressure, pressure);
        String pressureA11y = getString(R.string.a11y_pressure, pressureString);
        mDetailedWeatherBinding.extraDetails.pressure.setText(pressureString);
        mDetailedWeatherBinding.extraDetails.pressure.setContentDescription(pressureA11y);
        mDetailedWeatherBinding.extraDetails.pressureLabel.setContentDescription(pressureA11y);
        mForecastSummary = String.format("%s - %s - %s/%s",
                dateText, description, highString, lowString);

        /********
         * Wind *
         ********/
        float windSpeed = data.getFloat(INDEX_WEATHER_WIND_SPEED);
        String windString = AppWeatherUtils.getFormattedWind(this, windSpeed);
        String windA11y = getString(R.string.a11y_wind, windString);
        mDetailedWeatherBinding.extraDetails.windMeasurement.setText(windString);
        mDetailedWeatherBinding.extraDetails.windMeasurement.setContentDescription(windA11y);
        mDetailedWeatherBinding.extraDetails.windLabel.setContentDescription(windA11y);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}