package com.x.framework.redis.conf;

import cn.hutool.core.util.StrUtil;
import com.x.common.utils.JsonUtil;
import com.x.framework.redis.constants.RedisCommonConstant;
import com.x.framework.redis.property.RedissonClusterProperty;
import com.x.framework.redis.property.RedissonMasterSlaveProperty;
import com.x.framework.redis.property.RedissonSingleProperty;
import com.x.framework.redis.property.TtlRedisCacheManager;
import com.x.framework.redis.type.RedissonType;
import com.x.framework.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * @author : xuemingqi
 * @since : 2023/1/7 16:38
 */
@Slf4j
@Configuration
@ConditionalOnClass(RedissonClient.class)
@ConditionalOnProperty(prefix = "spring.redis.redisson", name = "type")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class RedissonConfig {

    private final RedissonSingleProperty singleProperty;

    private final RedissonMasterSlaveProperty masterSlaveProperty;

    private final RedissonClusterProperty clusterProperty;

    private final RedissonType redissonType;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        switch (redissonType.getType()) {
            case SINGLE -> createSingleConfig(config);
            case MASTER_SLAVE -> createMasterSlaveConfig(config);
            case CLUSTER -> createClusterConfig(config);
        }
        return Redisson.create(config);
    }

    /**
     * redisUtil bean
     */
    @Bean
    public RedisUtil redisUtil(RedissonClient redissonClient) {
        return new RedisUtil(redissonClient);
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
        return new RedissonConnectionFactory(redissonClient);
    }

    /**
     * redis cache
     */
    @Bean(RedisCommonConstant.CACHE_MANAGER_NAME)
    public RedisCacheManager selfCacheManager(RedissonConnectionFactory redissonConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redissonConnectionFactory);
        return new TtlRedisCacheManager(redisCacheWriter, RedisCacheConfiguration.defaultCacheConfig());
    }


    private void createSingleConfig(Config config) {
        config.useSingleServer()
                .setAddress(singleProperty.getAddress())
                .setConnectionPoolSize(singleProperty.getConnectionPoolSize())
                .setIdleConnectionTimeout(singleProperty.getIdleConnectionTimeout())
                .setConnectTimeout(singleProperty.getConnectTimeout())
                .setConnectionMinimumIdleSize(singleProperty.getConnectionMinimumIdleSize())
                .setTimeout(singleProperty.getTimeout());
        if (StrUtil.isNotBlank(singleProperty.getPassword())) {
            config.useSingleServer().setPassword(singleProperty.getPassword());
        }
        config.setCodec(new JsonJacksonCodec(JsonUtil.OBJECT_MAPPER));
    }

    private void createMasterSlaveConfig(Config config) {
        config.useMasterSlaveServers()
                .setConnectTimeout(masterSlaveProperty.getConnectTimeout())
                .setIdleConnectionTimeout(masterSlaveProperty.getIdleConnectionTimeout())
                .setMasterConnectionPoolSize(masterSlaveProperty.getMasterConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(masterSlaveProperty.getMasterConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(masterSlaveProperty.getSlaveConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(masterSlaveProperty.getSlaveConnectionMinimumIdleSize())
                .setReadMode(ReadMode.MASTER)
                .setMasterAddress(masterSlaveProperty.getMasterAddress())
                .setSlaveAddresses(masterSlaveProperty.getSlaveAddress());
        if (StrUtil.isNotBlank(masterSlaveProperty.getPassword())) {
            config.useMasterSlaveServers().setPassword(masterSlaveProperty.getPassword());
        }
        config.setCodec(new JsonJacksonCodec(JsonUtil.OBJECT_MAPPER));
    }

    private void createClusterConfig(Config config) {
        config.useClusterServers()
                .addNodeAddress(clusterProperty.getNodeAddresses())
                .setConnectTimeout(clusterProperty.getConnectTimeout())
                .setIdleConnectionTimeout(clusterProperty.getIdleConnectionTimeout())
                .setTimeout(clusterProperty.getTimeout())
                .setMasterConnectionMinimumIdleSize(clusterProperty.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(clusterProperty.getMasterConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(clusterProperty.getMasterConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(clusterProperty.getSlaveConnectionPoolSize())
                .setRetryAttempts(clusterProperty.getRetryAttempts())
                .setLoadBalancer(new RoundRobinLoadBalancer())
                .setRetryInterval(clusterProperty.getRetryInterval())
                .setSubscriptionsPerConnection(clusterProperty.getSubscriptionsPerConnection())
                .setScanInterval(clusterProperty.getScanInterval())
                .setReadMode(ReadMode.MASTER_SLAVE);
        if (StrUtil.isNotBlank(clusterProperty.getPassword())) {
            config.useClusterServers().setPassword(clusterProperty.getPassword());
        }
        config.setCodec(new JsonJacksonCodec(JsonUtil.OBJECT_MAPPER));
    }
}
