package com.x.config.snowflake.conf;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.x.config.snowflake.property.SnowflakeProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : xuemingqi
 * @since : 2023/1/8 19:50
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SnowflakeConfig {

    private final SnowflakeProperty property;

    @Bean
    public Snowflake snowflake() {
        return IdUtil.getSnowflake(property.getWorkerId(), property.getDataCenterId());
    }

}
