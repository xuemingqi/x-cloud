package com.x.config.snowflake.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/1/8 19:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "id-worker")
public class SnowflakeProperty {
    private Integer workerId;

    private Integer dataCenterId;
}
