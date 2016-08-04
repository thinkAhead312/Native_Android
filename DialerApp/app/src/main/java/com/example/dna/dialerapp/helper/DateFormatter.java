package com.example.dna.dialerapp.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dna on 8/4/16.
 */
public class DateFormatter {
    public void DateFormatter() {}

    public static String formatDate(String date) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));
        return formatter.format(calendar.getTime());
    }

}
