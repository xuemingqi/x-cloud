package com.x.work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 17:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableAsync
@ComponentScan({"com.x.work", "com.x.config", "com.x.auth"})
@MapperScan(basePackages = "com.x.work.db.mapper")
@EnableFeignClients({"com.x.auth.api", "com.x.websocket.api"})
public class WorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkApplication.class, args);
    }
}
