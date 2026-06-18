package com.x.framework.redis.autoconfigure;

import com.x.framework.redis.aspect.AccessLimitAspect;
import com.x.framework.redis.aspect.RedisLockAspect;
import com.x.framework.redis.conf.RedissonConfig;
import com.x.framework.redis.property.RedissonClusterProperty;
import com.x.framework.redis.property.RedissonMasterSlaveProperty;
import com.x.framework.redis.property.RedissonSingleProperty;
import com.x.framework.redis.type.RedissonType;
import com.x.framework.redis.type.RedissonTypeConverter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
        AccessLimitAspect.class,
        RedissonClusterProperty.class,
        RedissonConfig.class,
        RedissonMasterSlaveProperty.class,
        RedissonSingleProperty.class,
        RedissonType.class,
        RedissonTypeConverter.class,
        RedisLockAspect.class
})
public class XFrameworkRedisAutoConfiguration {
}
