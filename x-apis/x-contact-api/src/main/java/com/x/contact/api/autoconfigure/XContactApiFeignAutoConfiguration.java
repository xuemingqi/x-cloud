package com.x.contact.api.autoconfigure;

import com.x.contact.fallback.GroupApiFallbackFactory;
import com.x.contact.fallback.UserApiFallbackFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@ConditionalOnClass(FeignClient.class)
@EnableFeignClients(basePackages = "com.x.contact.api")
@Import({
        GroupApiFallbackFactory.class,
        UserApiFallbackFactory.class
})
public class XContactApiFeignAutoConfiguration {
}
