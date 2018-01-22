package com.marcinkaminski.aWeather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Creates datebase for our weather data. Used by WeatherProvider
public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 3;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    Creates new datebase table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                WeatherContract.WeatherEntry._ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WeatherContract.WeatherEntry.COLUMN_DATE       + " INTEGER NOT NULL, "                 +
                WeatherContract.WeatherEntry.COLUMN_WEATHER_ID + " INTEGER NOT NULL,"                  +
                WeatherContract.WeatherEntry.COLUMN_MIN_TEMP   + " REAL NOT NULL, "                    +
                WeatherContract.WeatherEntry.COLUMN_MAX_TEMP   + " REAL NOT NULL, "                    +
                WeatherContract.WeatherEntry.COLUMN_HUMIDITY   + " REAL NOT NULL, "                    +
                WeatherContract.WeatherEntry.COLUMN_PRESSURE   + " REAL NOT NULL, "                    +
                WeatherContract.WeatherEntry.COLUMN_WIND_SPEED + " REAL NOT NULL, "                    +
                " UNIQUE (" + WeatherContract.WeatherEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

//    Deletes old and creates new one
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WeatherContract.WeatherEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}