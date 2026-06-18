package com.x.framework.core.autoconfigure;

import com.x.framework.core.snowflake.conf.SnowflakeConfig;
import com.x.framework.core.snowflake.property.SnowflakeProperty;
import com.x.framework.core.system.SystemConfiguration;
import com.x.framework.core.thread.config.ThreadPoolTaskConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
        SystemConfiguration.class,
        SnowflakeConfig.class,
        SnowflakeProperty.class,
        ThreadPoolTaskConfig.class
})
public class XFrameworkCoreAutoConfiguration {
}
