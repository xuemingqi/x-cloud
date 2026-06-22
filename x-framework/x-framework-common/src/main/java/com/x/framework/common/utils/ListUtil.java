package com.x.framework.common.utils;

import com.x.framework.common.functional.PropertyCopierBiConsumer;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

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
        return copyList(sourceList, clazz, BeanUtils::copyProperties);
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
        return sourceList.stream().map(source -> {
            try {
                T target = targetClass.getDeclaredConstructor().newInstance();
                copier.copyProperties(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
