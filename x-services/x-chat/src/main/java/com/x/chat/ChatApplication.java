package com.x.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan(basePackages = {"com.x.chat.db.mapper"})
@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
