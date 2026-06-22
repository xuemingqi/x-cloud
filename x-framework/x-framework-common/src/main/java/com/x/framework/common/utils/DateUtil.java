package com.x.framework.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author : xuemingqi
 * @since : 2023/1/14 15:42
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {

    /**
     * 获取毫秒级时间戳
     */
    public static Long getTimestamp() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 获取秒级时间戳
     */
    public static Long getTimestampForSecond() {
        return Instant.now().getEpochSecond();
    }


    public static LocalDateTime parseLinuxTime(String dateTimeStr) {
        String[] times = dateTimeStr.split("/");
        //转换月份部分
        String monthPart = times[1];
        dateTimeStr = dateTimeStr.replaceAll(monthPart, parseMonth(monthPart));
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss Z"));
    }

    private static String parseMonth(String monthPart) {
        return switch (monthPart) {
            case "Jan" -> "01";
            case "Feb" -> "02";
            case "Mar" -> "03";
            case "Apr" -> "04";
            case "May" -> "05";
            case "Jun" -> "06";
            case "Jul" -> "07";
            case "Aug" -> "08";
            case "Sep" -> "09";
            case "Oct" -> "10";
            case "Nov" -> "11";
            case "Dec" -> "12";
            default -> throw new DateTimeParseException("Invalid month: " + monthPart, monthPart, 0);
        };
    }
}
