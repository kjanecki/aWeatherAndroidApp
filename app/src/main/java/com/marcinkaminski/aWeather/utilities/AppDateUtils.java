package com.marcinkaminski.aWeather.utilities;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.marcinkaminski.aWeather.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

// Class used for date convertions etc,
public final class AppDateUtils {
    public static final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);

//    Normalized UTC midnight milliseconds
    public static long getNormalizedUtcDateForToday() {
        long utcNowMillis = System.currentTimeMillis();
        TimeZone currentTimeZone = TimeZone.getDefault();
        long gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis);
        long timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis;
        long daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis);
        return TimeUnit.DAYS.toMillis(daysSinceEpochLocal);
    }

    private static long elapsedDaysSinceEpoch(long utcDate) {
        return TimeUnit.MILLISECONDS.toDays(utcDate);
    }

//    Miliseconds from epoch to today (at midnight UTC)
    public static long normalizeDate(long date) {
        return elapsedDaysSinceEpoch(date) * DAY_IN_MILLIS;
    }

//    Checks if date is normalized (to full day) or not
    public static boolean isDateNormalized(long millisSinceEpoch) {
        return millisSinceEpoch % DAY_IN_MILLIS == 0;
    }

//    Returns local date from normalized date
    private static long getLocalMidnightFromNormalizedUtcDate(long normalizedUtcDate) {
        TimeZone timeZone = TimeZone.getDefault();
        return normalizedUtcDate - timeZone.getOffset(normalizedUtcDate);
    }

//    Returns formatted date as string
    public static String getFriendlyDateString(Context context, long normalizedUtcMidnight, boolean showFullDate) {
        long localDate = getLocalMidnightFromNormalizedUtcDate(normalizedUtcMidnight);
        long daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(localDate);
        long daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis());

        if (daysFromEpochToProvidedDate == daysFromEpochToToday || showFullDate) {
            String dayName = getDayName(context, localDate);
            String readableDate = getReadableDateString(context, localDate);
//            If < 2 days, is sets Today, Tomorrow as day name
            if (daysFromEpochToProvidedDate - daysFromEpochToToday < 2) {
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName, dayName);
            } else {
                return readableDate;
            }
        } else if (daysFromEpochToProvidedDate < daysFromEpochToToday + 7) {
            return getDayName(context, localDate);
        } else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }

//    Returns formatted date as string. Difference between this one and getFriendlyDate is thath above method sets the 'friendly' name of day ex. 'Today'.
    private static String getReadableDateString(Context context, long timeInMillis) {
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;
        return DateUtils.formatDateTime(context, timeInMillis, flags);
    }

//    Sets (if possible) the 'friendly' day name, ex. 'Today'
    private static String getDayName(Context context, long dateInMillis) {
        long daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(dateInMillis);
        long daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis());
        int dayInWeek = (int) (daysFromEpochToProvidedDate - daysFromEpochToToday);

        switch (dayInWeek) {
            case 0:
                return context.getString(R.string.today);
            case 1:
                return context.getString(R.string.tomorrow);
            default:
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                return dayFormat.format(dateInMillis);
        }
    }
}