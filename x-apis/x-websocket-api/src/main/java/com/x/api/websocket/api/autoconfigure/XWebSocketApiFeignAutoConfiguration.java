package com.x.api.websocket.api.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@AutoConfiguration
@ConditionalOnClass(FeignClient.class)
@EnableFeignClients(basePackages = "com.x.api.websocket.api")
public class XWebSocketApiFeignAutoConfiguration {
}
