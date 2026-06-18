package com.x.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableAsync
public class WebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }

}
