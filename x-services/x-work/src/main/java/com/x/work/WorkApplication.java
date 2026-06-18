package com.x.work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 17:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableAsync
@EnableRetry
@MapperScan(basePackages = {"com.x.work.db.mapper"})
public class WorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkApplication.class, args);
    }
}
