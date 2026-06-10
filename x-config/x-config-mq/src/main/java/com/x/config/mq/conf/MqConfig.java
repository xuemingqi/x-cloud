package com.x.config.mq.conf;

import com.x.config.mq.util.MqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : xuemingqi
 * @since : 2023/2/5 10:28
 */
@Slf4j
@Configuration
@ConditionalOnClass(RocketMQTemplate.class)
public class MqConfig {

    /**
     * mq bean
     */
    @Bean
    public MqUtil mqUtil(RocketMQTemplate rocketMQTemplate) {
        return new MqUtil(rocketMQTemplate);
    }
}
