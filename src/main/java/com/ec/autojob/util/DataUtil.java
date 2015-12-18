package com.ec.autojob.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ecuser on 2015/12/17.
 */
public class DataUtil {

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static void main(String[] args) {
        SimpleDateFormat spf = new SimpleDateFormat("yyy-MM-dd 00:00:00");
        SimpleDateFormat spf2 = new SimpleDateFormat("yyy-MM-dd 23:59:59");
        System.out.println(spf2.format(getNextDay(new Date())));
        System.out.println(getNextDay(new Date()));
    }
}
