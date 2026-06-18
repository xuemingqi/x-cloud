package com.x.framework.mq.condition;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 18:18
 */
@Slf4j
public class OnRocketMqOrKafkaCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean hasRocketMq = false;
        boolean hasKafka = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            if (classLoader != null) {
                try {
                    classLoader.loadClass(RocketMQTemplate.class.getName());
                    hasRocketMq = true;
                } catch (Throwable e) {
                    log.info("RocketMQTemplate class not found, skipping RocketMQ support.");
                }
                try {
                    classLoader.loadClass(KafkaTemplate.class.getName());
                    hasKafka = true;
                } catch (Throwable  e) {
                    log.info("KafkaTemplate class not found, skipping Kafka support.");
                }
            }
            log.info("hasRocketMq:{}, hasKafka:{}", hasRocketMq, hasKafka);
            return hasRocketMq || hasKafka;
        } catch (Throwable e) {
            log.error("error in OnRocketMqOrKafkaCondition: {}", ExceptionUtils.getStackTrace(e));
            return false;
        }
    }
}
