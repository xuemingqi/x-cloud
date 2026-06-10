package com.x.config.redis.property;

import lombok.Data;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/1/12 9:07
 */
@Data
@Component
@RefreshScope
@ConditionalOnClass(RedissonClient.class)
@ConfigurationProperties(prefix = "spring.redis.redisson.cluster")
public class RedissonClusterProperty {

    private String[] nodeAddresses;

    private String password;

    private int connectTimeout = 10000;

    private int idleConnectionTimeout = 10000;

    private int timeout = 3000;

    private int masterConnectionMinimumIdleSize = 2;

    private int masterConnectionPoolSize = 10;

    private int slaveConnectionMinimumIdleSize = 2;

    private int slaveConnectionPoolSize = 10;

    private int retryAttempts = 3;

    private int retryInterval = 15;

    private int subscriptionsPerConnection = 6;

    private int scanInterval = 1000;

}
