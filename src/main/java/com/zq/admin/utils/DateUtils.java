package com.zq.admin.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @program: admin
 * @description: 日期工具类
 * @author: zhouQ
 * @create: 2020-11-18 11:29
 **/
public class DateUtils {

    /***
     * 获取时间格式
     * @param date 日期
     * @param formatStr 时间格式
     * @return
     */
    public static String getTime(Date date, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /***
     * 获取当前日期 线程安全
     * @return
     */
    public static String getNowDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /***
     * 获取当前日期时间 线程安全
     * @return
     */
    public static String getNowDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
