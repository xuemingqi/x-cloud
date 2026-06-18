package com.x.work.common.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : xuemingqi
 * @since : 2025/12/24 14:58
 */
@Configuration
public class ThreadConfiguration {

    @Bean(name = "virtualExecutor")
    public ExecutorService virtualExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
