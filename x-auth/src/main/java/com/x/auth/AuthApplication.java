package com.x.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : xuemingqi
 * @since : 2023/1/6 15:07
 */

@SpringBootApplication
@EnableFeignClients({"com.x.auth.api", "com.x.contact.api"})
@EnableDiscoveryClient
@ComponentScan({"com.x.auth", "com.x.config"})
@MapperScan(basePackages = "com.x.auth.db.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
