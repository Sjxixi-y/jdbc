package com.itsjxixi.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {
    // Date -> SQL.Date
    public static java.sql.Date toSQLDate(Date d1) {
        return new java.sql.Date(d1.getTime());
    }

    // Date -> String
    public static String toString(Date d1) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S E").format(d1);
    }

    // String -> Date
    public static Date toUtilDate(String str) {
        return java.sql.Date.valueOf(str);
    }
}
