package com.x.framework.mq.autoconfigure;

import com.x.framework.mq.conf.KafkaConfig;
import com.x.framework.mq.conf.RocketMqConfig;
import com.x.framework.mq.property.MqProperty;
import com.x.framework.mq.property.MqTypeConverter;
import com.x.framework.mq.service.impl.MqServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({
        KafkaConfig.class,
        MqProperty.class,
        MqServiceImpl.class,
        MqTypeConverter.class,
        RocketMqConfig.class
})
public class XFrameworkMqAutoConfiguration {
}
