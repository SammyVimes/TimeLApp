package com.danilov.TimeLApp.core.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Arrays;
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

    private Util() {

    }

}
