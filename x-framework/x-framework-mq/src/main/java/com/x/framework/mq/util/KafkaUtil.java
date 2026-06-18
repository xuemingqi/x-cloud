package com.x.framework.mq.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.util.concurrent.CompletableFuture;

/**
 * @author : xuemingqi
 * @since : 2024-09-18 09:29
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Slf4j
public class KafkaUtil {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public KafkaUtil(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 发送消息
     *
     * @param topic   主题
     * @param message 消息
     * @return 发送结果
     */
    public CompletableFuture<SendResult<String, String>> send(String topic, String message) {
        return kafkaTemplate.send(topic, message);
    }

    /**
     * 发送消息
     *
     * @param topic 主题
     * @param key   键
     * @param data  数据
     * @return 发送结果
     */
    public CompletableFuture<SendResult<String, String>> send(String topic, String key, String data) {
        return kafkaTemplate.send(topic, key, data);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @return 发送结果
     */
    public CompletableFuture<SendResult<String, String>> send(Message<?> message) {
        return kafkaTemplate.send(message);
    }
}
