package com.example.gabrielcardoso.possogastar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by dfavato on 10/12/16.
 */

public class Utils {
    public static Date getFirstDayOfCurrentMonth() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DATE, 1);
        return today.getTime();
    }
    public static  Date getLastDayOfCurrentMonth() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.MONTH, today.get(Calendar.MONTH) + 1);
        today.set(Calendar.DATE, 0);
        return today.getTime();
    }

    public static Date today() {
        return Calendar.getInstance().getTime();
    }
}
