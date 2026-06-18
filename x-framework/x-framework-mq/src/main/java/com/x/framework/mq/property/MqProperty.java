package com.x.framework.mq.property;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : xuemingqi
 * @since : 2024-08-30 17:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "mq")
public class MqProperty {

    private MqTypeEnum type;

    @Getter
    @RequiredArgsConstructor
    public enum MqTypeEnum {

        ROCKETMQ("rocketmq"),
        KAFKA("kafka"),
        RABBITMQ("rabbitmq");


        private final String value;

        public static MqTypeEnum fromType(String key) {
            return Arrays.stream(values())
                    .filter(o -> key.equals(o.getValue()))
                    .findAny().orElse(null);
        }

    }
}
