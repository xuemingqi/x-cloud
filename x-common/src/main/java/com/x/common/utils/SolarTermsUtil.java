package com.x.common.utils;

import cn.hutool.core.date.chinese.SolarTerms;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolarTermsUtil {

    /**
     * 获取24节气日期
     *
     * @param year 年份
     * @return 24节气日期
     */
    public static List<LocalDate> getSolarTermsDate(int year) {
        LocalDate[] startDate = {LocalDate.of(year, 1, 1)};
        return IntStream.range(1, 25)
                .mapToObj(i -> {
                    int newDay = SolarTerms.getTerm(year, i);
                    if (i > 1 && newDay < SolarTerms.getTerm(year, i - 1)) {
                        startDate[0] = startDate[0].plusMonths(1);
                    }
                    return startDate[0].plusDays(newDay - 1);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取24节气名称
     *
     * @param date 日期
     * @return 节气名称
     */
    public static String getTerm(LocalDate date) {
        return SolarTerms.getTerm(date);
    }

    /**
     * 获取24节气名称和对应日期
     *
     * @param year 年份
     * @return 24节气名称和对应日期
     */
    public static Map<LocalDate, String> getSolarTerms(int year) {
        return getSolarTermsDate(year).stream()
                .collect(Collectors.toMap(date -> date, SolarTermsUtil::getTerm, (existing, replacement) -> existing, LinkedHashMap::new));
    }
}
