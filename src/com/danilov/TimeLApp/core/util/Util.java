package com.danilov.TimeLApp.core.util;

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

    private Util() {

    }

}
