package com.x.config.redis.property;

import lombok.Data;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/1/12 9:06
 */
@Data
@Component
@RefreshScope
@ConditionalOnClass(RedissonClient.class)
@ConfigurationProperties(prefix = "spring.redis.redisson.single")
public class RedissonSingleProperty {

    private String address;

    private String password;

    private int connectTimeout = 10000;

    private int connectionPoolSize = 50;

    private int idleConnectionTimeout = 10000;

    private int connectionMinimumIdleSize = 20;

    private int timeout = 3000;

}
