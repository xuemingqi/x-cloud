package com.x.common.utils;

import com.x.common.functional.PropertyCopierBiConsumer;

import java.util.function.*;

/**
 * @author : xuemingqi
 * @since : 2024-10-16 09:54
 */
public class FunUtil {


    public static <T> Consumer<T> wrap(Consumer<T> consumer) {
        return consumer;
    }

    public static <T, U> BiConsumer<T, U> wrap(BiConsumer<T, U> biConsumer) {
        return biConsumer;
    }

    public static <T, R> Function<T, R> wrap(Function<T, R> function) {
        return function;
    }

    public static <T, U, R> BiFunction<T, U, R> wrap(BiFunction<T, U, R> biFunction) {
        return biFunction;
    }

    public static <T> Supplier<T> wrap(Supplier<T> supplier) {
        return supplier;
    }

    public static IntSupplier wrap(IntSupplier intSupplier) {
        return intSupplier;
    }

    public static <T> UnaryOperator<T> wrap(UnaryOperator<T> unaryOperator) {
        return unaryOperator;
    }

    public static <T> Predicate<T> wrap(Predicate<T> predicate) {
        return predicate;
    }

    public static <T, U> BiPredicate<T, U> wrap(BiPredicate<T, U> biPredicate) {
        return biPredicate;
    }

    public static <S, T> PropertyCopierBiConsumer<S, T> wrap(PropertyCopierBiConsumer<S, T> propertyCopierBiConsumer) {
        return propertyCopierBiConsumer;
    }
}
