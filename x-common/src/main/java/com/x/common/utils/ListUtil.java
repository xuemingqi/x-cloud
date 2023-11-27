package com.x.common.utils;

import com.x.common.functional.PropertyCopierBiConsumer;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    /**
     * 函数式方法拷贝对象，默认采用BeanUtils.copyProperties(Object source, Object target)
     *
     * @param sourceList 源对象
     * @param clazz      目标对象类型
     * @param <T>        目标对象类型
     * @return 拷贝
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        try {
            for (S source : sourceList) {
                T target = clazz.newInstance();
                BeanUtils.copyProperties(source, target);
                result.add(target);
            }
            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 函数式方法拷贝对象
     *
     * @param sourceList  源对象
     * @param targetClass 目标对象类型
     * @param copier      拷贝函数
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 拷贝结果
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass, PropertyCopierBiConsumer<S, T> copier) {
        List<T> result = new ArrayList<>();
        try {
            for (S source : sourceList) {
                T target = targetClass.newInstance();
                copier.copyProperties(source, target);
                result.add(target);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
