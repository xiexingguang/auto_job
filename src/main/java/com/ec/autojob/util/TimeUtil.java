package com.ec.autojob.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @ClassName: TimeUtil
 * @Description: TODO
 * @author
 * @date 2015年8月4日 下午4:42:19
 */
public class TimeUtil {
    /**
     * 判断是否date0和date1表示的日期是否是同年同月同周
     *
     * @param date0
     *            整数表示的日期,格式yyyymmdd
     * @param date1
     *            整数表示的日期,格式yyyymmdd
     * @return true 表示是同年同月同周
     */
    public static boolean isSameWeek(int date0, int date1) {
        int y0 = date0 / 10000;
        int m0 = (date0 % 10000) / 100;

        int y1 = date1 / 10000;
        int m1 = (date1 % 10000) / 100;

        if (y0 != y1 || m0 != m1)
            return false;

        int d0 = date0 % 100;
        int d1 = date1 % 100;

        Calendar cal = Calendar.getInstance();
        cal.set(y0, m0 - 1, d0);
        int week_of_year0 = cal.get(Calendar.WEEK_OF_YEAR);
        cal.set(y1, m1 - 1, d1);
        int week_of_year1 = cal.get(Calendar.WEEK_OF_YEAR);

        if (week_of_year0 == week_of_year1)
            return true;
        return false;
    }

    /**
     * 判断是否date0和date1表示的日期是否是同年同月
     *
     * @param date0
     *            整数表示的日期,格式yyyymmdd
     * @param date1
     *            整数表示的日期,格式yyyymmdd
     * @return true 表示是同年同月同周
     */
    public static boolean isSameMonth(int date0, int date1) {
        int y0 = date0 / 10000;
        int m0 = (date0 % 10000) / 100;

        int y1 = date1 / 10000;
        int m1 = (date1 % 10000) / 100;

        if (y0 != y1 || m0 != m1)
            return false;
        return true;
    }

    /**
     * 获得Calendar类型的日期实例日期的整数表示
     *
     * @param cal
     *            Calendar类型的日期实例
     * @return 日期的整数表示,格式yyyymmdd
     */
    public static int getDate(Calendar cal) {
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前的日期的整数表示
     *
     * @return 日期的整数表示,格式yyyymmdd
     */
    public static int getDate() {
        return getDate(Calendar.getInstance());
    }

    /**
     * 获得Calendar类型的日期实例时间的整数表示
     *
     * @param cal
     *            日历对象
     * @return 当天经过的秒数
     */
    public static int getTime(Calendar cal) {
        return cal.get(Calendar.HOUR_OF_DAY) * 3600 + cal.get(Calendar.MINUTE) * 60 + cal.get(Calendar.SECOND);
    }

    /**
     * 描述:获取两日期间隔天数. <br/>
     *
     * @author Administrator
     * @param d1
     * @param d2
     * @return
     * @throws Exception
     */
    public static int dateDiff(long d1, long d2) throws Exception {
        Long diff = Math.abs(d1 - d2);
        diff /= 3600 * 1000 * 24;
        return diff.intValue();
    }

    /**
     * 获得当前的时间的整数表示
     *
     * @return 当天经过的秒数
     */
    public static int getTime() {
        return getTime(Calendar.getInstance());
    }

    /**
     * 获得当前的日期的整数表示
     *
     * @param cal
     *            Calendar类型的日期实例
     * @return 日期的整数表示,格式YYYYMMDDHHMMSS
     */
    public static long getDateTime(Calendar cal) {
        long ymd = cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
        long hms = cal.get(Calendar.HOUR_OF_DAY) * 10000 + cal.get(Calendar.MINUTE) * 100 + cal.get(Calendar.SECOND);
        long ret = ymd * 1000000 + hms;
        return ret;
    }

    /**
     * 获得当前的日期的整数表示
     *
     * @return 日期的整数表示,格式YYYYMMDDHHMMSS
     */
    public static long getDateTime() {
        return getDateTime(Calendar.getInstance());
    }

    /**
     * 获得当前日期的字符串
     *
     * @param cal
     *            Calendar类型的日期实例
     * @return 当天的日期字符串"yyyy-mm-dd"
     */
    public static String getDateStr(Calendar cal) {
        String a = "" + getDate(cal);

        StringBuffer sb = new StringBuffer(10);
        sb.append(a.substring(0, 4));
        sb.append("-");
        sb.append(a.substring(4, 6));
        sb.append("-");
        sb.append(a.substring(6, 8));
        return sb.toString();
    }

    /**
     *
     * @Title: getDateStr
     * @Description: TODO
     * @param date
     * @return
     * @return String
     * @throws
     */
    public static String getDateStr(Date date) {
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (null != date) {
                dateStr = sdf.format(date);
            }
        } catch (Exception e) {
        }
        return dateStr;
    }

    /**
     * 获得当前日期的字符串
     *
     * @return 当天的日期字符串"yyyy-mm-dd"
     */
    public static String getDateStr() {
        return getDateStr(Calendar.getInstance());
    }

    public static Date getNowDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(getDateStr(Calendar.getInstance()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据经过的毫秒数获得时间字符串
     *
     * @param milliseconds
     *            经过的毫秒数
     * @return 时间字符串"hh:mm:ss"
     */
    public static String getTimeStr(long milliseconds) {
        long seconds = milliseconds / 1000;
        long h = seconds / 3600;
        long m = seconds % 3600 / 60;
        long s = seconds % 60;

        StringBuffer sb = new StringBuffer(10);
        if (h < 10)
            sb.append(0);
        sb.append(h);
        sb.append(":");
        if (m < 10)
            sb.append(0);
        sb.append(m);
        sb.append(":");
        if (s < 10)
            sb.append(0);
        sb.append(s);

        return sb.toString();
    }

    /**
     * 获得Calendar类型的时间字符串
     *
     * @param cal
     *            Calendar类型的日期实例
     * @return 时间字符串"hh:mm:ss"
     */
    public static String getTimeStr(Calendar cal) {
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        StringBuffer sb = new StringBuffer(10);
        if (h < 10)
            sb.append(0);
        sb.append(h);
        sb.append(":");
        if (m < 10)
            sb.append(0);
        sb.append(m);
        sb.append(":");
        if (s < 10)
            sb.append(0);
        sb.append(s);

        return sb.toString();
    }

    /**
     * 获得当前时间的字符串
     *
     * @return 时间字符串"hh:mm:ss"
     */
    public static String getTimeStr() {
        return getTimeStr(Calendar.getInstance());
    }

    /**
     * 获取当天的时期时间字符串
     *
     * @return 日期时间字符串"yyyy-mm-hh hh:mm:ss"
     */
    public static String getDateTimeStr() {
        return getDateTimeStr(Calendar.getInstance());
    }

    /**
     * 获取cal指定的时期时间字符串
     *
     * @param cal
     *            Calendar类型的日期实例
     * @return 日期时间字符串"yyyy-mm-hh hh:mm:ss"
     */
    public static String getDateTimeStr(Calendar cal) {
        StringBuffer sb = new StringBuffer(20);
        sb.append(getDateStr(cal));
        sb.append(" ");
        sb.append(getTimeStr(cal));
        return sb.toString();
    }

    /**
     * 获取当天的时期时间字符串
     *
     * @param milliseconds
     *            以毫秒为单位的当前时间
     * @return 日期时间字符串"yyyy-mm-hh hh:mm:ss"
     */
    public static String getDateTimeStr(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date(milliseconds));
    }

    /** 字符串转化为日期 */
    public static Date StrToDate(String str) {
        Date returnDate = null;
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                returnDate = sdf.parse(str);
            } catch (Exception e) {
                System.err.println("AppTools [Date StrToDate(String str)] Exception");
                return returnDate;
            }
        }
        return returnDate;
    }

    /** 字符串转化为日期 */
    public static Date StrToDate(String str, String formatStr) {
        Date returnDate = null;
        if (str != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            try {
                returnDate = sdf.parse(str);
            } catch (Exception e) {
                System.err.println("AppTools [Date StrToDate(String str)] Exception");
                return returnDate;
            }
        }
        return returnDate;
    }

    /**
     * 将字符转化成java.sql.Timestamp
     *
     * @param time
     * @return java.sql.Timestamp
     * @throws
     */
    public static java.sql.Timestamp getTimestamp(String time) {
        java.sql.Timestamp timestamp = null;
        java.util.Date utilDate;
        int pos = time.lastIndexOf(".");
        String temp = null;
        if (pos > 0) {
            temp = time.substring(0, time.lastIndexOf("."));
        } else {
            temp = time;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            utilDate = format.parse(temp);
            timestamp = new java.sql.Timestamp(utilDate.getTime());

        } catch (ParseException e) {
            timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        }
        return timestamp;
    }

    /**
     * 将字符转化成java.sql.Timestamp
     *
     * @param time
     * @return java.sql.Timestamp
     * @throws
     */
    public static java.sql.Timestamp getTimestamp(long time) {
        java.sql.Timestamp timestamp = null;
        try {
            timestamp = new java.sql.Timestamp(time);
        } catch (Exception e) {
            timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        }
        return timestamp;
    }

    /**
     * 获得当前时间与今日 23:00:00秒相差的毫秒数
     *
     * @return long
     * @throws
     */
    public static long getFixTime(int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        long time = calendar.getTimeInMillis() - now.getTimeInMillis();
        time = time < 0 ? 0 : time;
        if (time != 0) {
            time /= 1000;
        }
        return time;
    }

    /**
     * 得到当前时间 -periodTime时间
     *
     * @param periodTime
     *            差距时间
     * @return String
     * @throws
     */
    public static String getFixTime(int periodTime) {
        Calendar now = Calendar.getInstance();
        if (periodTime >= 0) {
            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - periodTime);
        }
        String t = getDateTimeStr(now);
        return t;

    }

    /**
     * 得到前一天的时间 YYYY-MM-DD
     *
     * @return String
     * @throws
     */
    public static String getLastDay(int t) {
        String lastDay = "";
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -t);
        lastDay = getDateStr(c);
        return lastDay;
    }

    /**
     * 计算字符串时间与当前相距天数
     *
     * @param str
     *            yyyy-mm-dd int 计算的天数
     * @throws
     */
    public static int calcuateDays(String str) {
        int days = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date someDate = null;
        Calendar now = Calendar.getInstance();
        try {
            if (null != str && !"".equals(str)) {
                someDate = sdf.parse(str);
            } else {
                someDate = now.getTime();
            }
        } catch (ParseException e) {
            someDate = now.getTime();
        }

        days = Integer.parseInt(String.valueOf((now.getTimeInMillis() - someDate.getTime()) / (3600L * 1000 * 24)));

        return days == 0 ? 4 : days;
    }

    /**
     * 描述:判断给定时间是否在某时间段内. <br/>
     *
     * @author Administrator
     * @param time
     * @param timeStart
     * @param timeEnd
     * @return
     */
    public static boolean inTimeRange(String time, String timeStart, String timeEnd) {
        int second, secondStart, secondEnd;
        String[] timeAry = time.split(":");
        String[] timeStartAry = timeStart.split(":");
        String[] timeEndAry = timeEnd.split(":");
        try {
            second = Integer.valueOf(timeAry[0]) * 3600 + Integer.valueOf(timeAry[1]) * 60 + Integer.valueOf(timeAry[2].substring(0, 2));
            secondStart = Integer.valueOf(timeStartAry[0]) * 3600 + Integer.valueOf(timeStartAry[1]) * 60
                            + Integer.valueOf(timeStartAry[2].substring(0, 2));
            secondEnd = Integer.valueOf(timeEndAry[0]) * 3600 + Integer.valueOf(timeEndAry[1]) * 60 + Integer.valueOf(timeEndAry[2].substring(0, 2));
        } catch (Exception e) {
            return true;
        }
        return second > secondStart && second < secondEnd ? true : false;
    }

    /**
     * millisecond值与当前系统时间比较，是否超过hour值
     *
     * @param millisecond
     *            时间毫秒
     * @param hour
     *            相差时间，单位（小时）
     * @return 超过返回true
     */
    public static boolean compareHour(Long millisecond, int hour) {
        return (System.currentTimeMillis() - millisecond) > (hour * 60 * 60 * 1000);
    }

    /**
     * 获取当前月份
     *
     * @Title: getNowMonth
     * @Description: TODO
     * @return
     * @return String
     * @throws
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return sdf.format(new Date());
    }

    /**
     * 获取当前季度
     *
     * @Title: getNowQuarter
     * @Description: TODO
     * @return
     * @return String
     * @throws
     */
    public static String getNowQuarter() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        // System.out.println(month);
        String Quarter = "";
        if (month == 0) {
            month = month + 1;
        }
        Quarter = month % 4 + "";
        return year + "0" + Quarter;

    }

    /**
     * 获取前一月月份
     *
     * @Title: getBeforeMonth
     * @Description: TODO
     * @return
     * @return String
     * @throws
     */

    public static String getBeforeMonth() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int Quarter = c.get(Calendar.MONTH);
        if (Quarter == 0) {
            return (year - 1) + "12";
        } else {
            return year + Quarter + "";
        }
    }

    public static String getBeforeMonthFormt() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int Quarter = c.get(Calendar.MONTH);
        if (Quarter == 0) {
            return (year - 1) + "-12";
        } else if (Quarter < 10) {
            return year + "-0" + Quarter;
        } else {
            return year + "-" + Quarter;
        }
    }

    public static String getBeforeYear() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int Quarter = c.get(Calendar.MONTH);
        if (Quarter == 0) {
            return (year - 1) + "";
        } else {
            return year + "";
        }
    }

    /**
     * 获取当前时间
     *
     * @Title: getNowTimestamp
     * @Description: TODO
     * @return
     * @return Timestamp
     * @throws
     */
    public static Timestamp getNowTimestamp() {
        Date date = new Date();
        Timestamp nousedate = new Timestamp(date.getTime());
        return nousedate;
    }

    public static void main(String[] args) {
        // System.out.println(getNowQuarter());

        // System.out.println(nousedate);
        System.out.println(getBeforeYear());
    }

}
