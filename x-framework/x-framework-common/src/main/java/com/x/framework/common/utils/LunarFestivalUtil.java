package com.x.framework.common.utils;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.chinese.LunarFestival;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LunarFestivalUtil {

    /**
     * 获取指定年份的农历节日
     *
     * @param year 年份
     * @return 农历节日
     */
    public static Map<LocalDate, List<String>> getFestivals(int year) {
        return Stream.iterate(LocalDate.of(year, 1, 1), date -> date.getYear() == year, date -> date.plusDays(1))
                .map(date -> {
                    ChineseDate chineseDate = new ChineseDate(date);
                    List<String> lunarList = LunarFestival.getFestivals(chineseDate.getChineseYear(), chineseDate.getMonth(), chineseDate.getDay());
                    return lunarList.isEmpty() ? null : Map.entry(date, lunarList);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing, LinkedHashMap::new));
    }
}
