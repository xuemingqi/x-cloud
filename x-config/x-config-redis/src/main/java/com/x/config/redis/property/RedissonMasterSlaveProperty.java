package com.x.config.redis.property;

import lombok.Data;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author : xuemingqi
 * @since : 2023/1/12 10:17
 */
@Data
@Component
@RefreshScope
@ConditionalOnClass(RedissonClient.class)
@ConfigurationProperties(prefix = "spring.redis.redisson.master-salve")
public class RedissonMasterSlaveProperty {

    private String masterAddress;

    private Set<String> slaveAddress;

    private String password;

    private int connectTimeout = 10000;

    private int idleConnectionTimeout = 10000;

    private int masterConnectionPoolSize = 100;

    private int masterConnectionMinimumIdleSize = 20;

    private int slaveConnectionPoolSize = 50;

    private int slaveConnectionMinimumIdleSize = 10;

    private int timeout = 3000;

}
