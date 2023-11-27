package com.x.contact;

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
@EnableDiscoveryClient
@ComponentScan({"com.x.contact", "com.x.config"})
@MapperScan(basePackages = "com.x.contact.db.mapper")
@EnableFeignClients({"com.x.auth.api"})
public class ContactApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactApplication.class, args);
    }
}
