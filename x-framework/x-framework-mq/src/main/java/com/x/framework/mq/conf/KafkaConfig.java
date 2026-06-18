package com.x.framework.mq.conf;

import com.x.framework.mq.util.KafkaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author : xuemingqi
 * @since : 2024-09-18 09:40
 */
@Slf4j
@Configuration
@ConditionalOnClass(EnableKafka.class)
public class KafkaConfig {

    @Bean
    public KafkaUtil kafkaUtil(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaUtil(kafkaTemplate);
    }
}
