package com.x.config.redis.type;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author : xuemingqi
 * @since : 2023/1/12 9:17
 */
@Component
@ConfigurationPropertiesBinding
public class RedissonTypeConverter implements Converter<String, RedissonType.Type> {
    @Override
    public RedissonType.Type convert(@Nonnull String s) {
        return RedissonType.Type.fromType(s);
    }
}
