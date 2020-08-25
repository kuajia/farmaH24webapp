package it.farmah24.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by CodaQ srls - Alessandro Bonetti on 29/12/2017.
 * All rights reserved. CodaQ © 2017
 */

public class DateUtil
{
    public static final String DATE_FORMAT      = "yyyy-MM-dd";
    public static final String TIME_FORMAT      = "HH:mm:ss";
    public static final String DATE_FORMAT_DMY  = "dd/MM/yyyy";

    //----------------------------------------------------------------------------------------------

    public static String formatDateYMD(long millis) {
        return formatDateYMD(new Date(millis));
    }

    public static String formatDateYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ITALIAN);
        return sdf.format(date);
    }


    public static String formatDateDMY(long millis) {
        return formatDateDMY(new Date(millis));
    }
    public static String formatDateDMY(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DMY, Locale.ITALIAN);
        return sdf.format(date);
    }

    public static String formatDateHMS(long millis) {
        return formatTimeHMS(new Date(millis));
    }

    public static String formatTimeHMS(Date time) {
        return formatDateHMS(time);
    }
    public static String formatDateHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ITALIAN);
        return sdf.format(date);
    }

    //----------------------------------------------------------------------------------------------

    public static Date parseDateYMD(String s, Date defaultValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ITALIAN);
        try {
            Date date = dateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    public static Date parseTimeHMS(String s, Date defaultValue) { return parseDateHMS(s, defaultValue); }
    public static Date parseDateHMS(String s, Date defaultValue) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.ITALIAN);
        try {
            Date date = dateFormat.parse(s);
            return date;
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    //----------------------------------------------------------------------------------------------

    public static String currentTimeString() {
        return formatDateHMS(new Date());
    }

    public static boolean isBetween(String open, String close) {
        return isBetween(currentTimeString(), open, close);
    }

    public static boolean isBetween(String curTime, String open, String close) {
        return (open.compareTo(curTime) <= 0 && close.compareTo(curTime) > 0);
    }

    public static Date hhmmssToTime(String time)
    {
        try {
            DateFormat df = new SimpleDateFormat("HH:mm:ss"); // or "hh:mm" for 12 hour format
            return df.parse(time);
        } catch (Exception e) {
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------

    public static int getDow(String sDate)
    {
        try {
            return getDow(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).parse(sDate));
        } catch (Exception e) {
            return -1;
        }
    }

    public static int getDow(Date date)
    {
        Calendar c = Calendar.getInstance(Locale.ITALIAN);
        c.setTime(date);
        int dow = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dow == 0)
            dow = 7;
        return dow;
    }

    public static String getDowName(String sDate)
    {
        try {
            return getDowName(new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN).parse(sDate));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDowName(Date date)
    {
        int dow = getDow(date);
        dow = (dow + 1) % 7;
        return new DateFormatSymbols(Locale.ITALIAN).getWeekdays()[dow];
    }

    public static String getDowName(int dayOfWeek)
    {
        if (dayOfWeek < 1 || dayOfWeek > 7)
             return null;

        int dow = dayOfWeek;
        dow = (dow + 1) % 7;
        return new DateFormatSymbols(Locale.ITALIAN).getWeekdays()[dow];
    }

}
