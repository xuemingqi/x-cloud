package com.x.common.functional;

/**
 * @author : xuemingqi
 * @since : 2023/8/9 10:55
 *
 * bean拷贝function
 */
@FunctionalInterface
public interface PropertyCopierBiConsumer<S, T> {
    void copyProperties(S source, T target);
}
