package com.x.config.redis.property;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;

public class TtlRedisCacheManager extends RedisCacheManager {

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(@Nonnull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        String[] cells = StringUtils.delimitedListToStringArray(name, "=");
        name = cells[0];
        if (cells.length > 1 && null != cacheConfig) {
            long ttl = Long.parseLong(cells[1]);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
        }
        return super.createRedisCache(name, cacheConfig);
    }

}
