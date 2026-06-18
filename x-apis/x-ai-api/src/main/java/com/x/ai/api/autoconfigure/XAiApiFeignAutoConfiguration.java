package com.x.ai.api.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

@AutoConfiguration
@ConditionalOnClass(FeignClient.class)
@EnableFeignClients(basePackages = "com.x.ai.api")
public class XAiApiFeignAutoConfiguration {
}
