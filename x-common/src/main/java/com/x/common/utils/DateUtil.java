package com.x.common.utils;

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
        switch (monthPart) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                throw new DateTimeParseException("Invalid month: " + monthPart, monthPart, 0);
        }
    }
}
