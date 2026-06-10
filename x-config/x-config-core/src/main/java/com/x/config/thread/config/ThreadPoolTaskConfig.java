package com.x.config.thread.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolTaskConfig {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 20;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 161;

    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int KEEP_ALIVE_TIME = 5;

    /**
     * 缓冲队列数
     */
    private static final int QUEUE_CAPACITY = 20;

    /**
     * 线程池名前缀
     */
    private static final String THREAD_NAME_PREFIX = "x-async-thread-";

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //队列数
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //空闲时间
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        //线程前缀
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        //等待线程执行完进程结束
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}
