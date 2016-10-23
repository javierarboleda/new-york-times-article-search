package com.javierarboleda.newyorktimesarticlesearch.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 10/23/16.
 */
public class Util {

    public static String getFormattedDateForQuery(Date date) {
        SimpleDateFormat monthDayYearFormat = new SimpleDateFormat("yyyyMMdd");
        return monthDayYearFormat.format(date);
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat monthDayYearFormat = new SimpleDateFormat("MMM d, yyyy");
        return monthDayYearFormat.format(date);
    }
}
