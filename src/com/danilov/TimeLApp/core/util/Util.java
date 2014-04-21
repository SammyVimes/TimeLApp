package com.danilov.TimeLApp.core.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Semyon Danilov on 18.04.2014.
 */
public final class Util {

    public static List<String> getMonths() {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        List<String> months = Arrays.asList(dateFormatSymbols.getMonths());
        return months;
    }

    public static void toast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static Calendar getClearMonth(final int year, final int month) {
        Calendar thisMonth = Calendar.getInstance();
        thisMonth.set(Calendar.YEAR, year);
        thisMonth.set(Calendar.MONTH, month);
        thisMonth.set(Calendar.DAY_OF_MONTH, 0);
        thisMonth.set(Calendar.HOUR_OF_DAY, 0);
        thisMonth.set(Calendar.MINUTE, 0);
        thisMonth.set(Calendar.SECOND, 0);
        thisMonth.set(Calendar.MILLISECOND, 0);
        return thisMonth;
    }

    public static Calendar getClearDay() {
        Calendar day = Calendar.getInstance();
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        return day;
    }

    private Util() {

    }

}
