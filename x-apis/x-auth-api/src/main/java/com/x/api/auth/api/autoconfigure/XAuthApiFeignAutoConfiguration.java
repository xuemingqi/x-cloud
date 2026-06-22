package com.x.api.auth.api.autoconfigure;

import com.x.api.auth.client.FeignAuthClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;

@AutoConfiguration(beforeName = "com.x.framework.web.autoconfigure.XFrameworkWebAutoConfiguration")
@ConditionalOnClass(FeignClient.class)
@EnableFeignClients(basePackages = "com.x.api.auth.api")
@Import(FeignAuthClient.class)
public class XAuthApiFeignAutoConfiguration {
}
