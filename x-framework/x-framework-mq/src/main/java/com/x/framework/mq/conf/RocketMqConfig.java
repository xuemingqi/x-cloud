package com.x.framework.mq.conf;

import com.x.framework.mq.util.RocketMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.MQAdmin;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author : xuemingqi
 * @since : 2023/2/5 10:28
 */
@Slf4j
@Configuration
@Import(RocketMQAutoConfiguration.class)
@ConditionalOnClass(MQAdmin.class)
public class RocketMqConfig {

    @Bean
    public RocketMqUtil rocketMqUtil(RocketMQTemplate rocketMQTemplate) {
        return new RocketMqUtil(rocketMQTemplate);
    }
}
