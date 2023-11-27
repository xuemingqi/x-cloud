package com.x.common.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author : xuemingqi
 * @since : 2023/9/14 13:05
 */
public class CompletableUtil {

    /**
     * 获取CompletableFuture的执行结果
     *
     * @param futures 任务集合
     * @param <T>     泛型参数
     * @return 执行结果
     */
    public static <T> List<T> get(List<CompletableFuture<T>> futures) {
        return futures.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * 等待任务完成
     *
     * @param futures 任务列表
     * @param <T>     任务泛型
     */
    public static <T> void join(List<CompletableFuture<T>> futures) {
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
