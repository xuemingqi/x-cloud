package com.x.config.mq.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;

import java.util.Collection;

/**
 * @author : xuemingqi
 * @since : 2023/2/5 10:15
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Slf4j
public class MqUtil {

    private final RocketMQTemplate rocketMQTemplate;

    public MqUtil(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 单条通知发送
     *
     * @param topic   主题
     * @param message 消息
     */
    public void convertAndSend(String topic, Object message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    /**
     * 批量通知发送
     *
     * @param topic    主题
     * @param messages 消息集合
     */
    public <T extends Message<?>> SendResult syncSend(String topic, Collection<T> messages) {
        return rocketMQTemplate.syncSend(topic, messages);
    }

    /**
     * 批量通知发送
     *
     * @param topic        主题
     * @param messages     消息集合
     * @param sendCallback 回调函数
     */
    public <T extends Message<?>> void asyncSend(String topic, Collection<T> messages, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, messages, sendCallback);
    }
}
