package com.x.config.redis.type;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : xuemingqi
 * @since : 2023/1/12 9:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis.redisson")
public class RedissonType {

    public Type type;

    @Getter
    @RequiredArgsConstructor
    public enum Type {

        SINGLE("SINGLE", "单机"),

        MASTER_SLAVE("MASTER_SLAVE", "主从"),

        CLUSTER("CLUSTER", "集群");

        private final String type;
        private final String memo;

        static Type fromType(String key) {
            return Arrays.stream(values())
                    .filter(o -> key.equals(o.getType()))
                    .findAny().orElse(null);
        }
    }
}
