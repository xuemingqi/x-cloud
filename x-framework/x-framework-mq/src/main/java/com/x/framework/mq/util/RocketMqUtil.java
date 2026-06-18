package com.x.framework.mq.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;

import java.util.Collection;

/**
 * @author : xuemingqi
 * @since : 2023/2/5 10:15
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Slf4j
public class RocketMqUtil {

    private final RocketMQTemplate rocketMQTemplate;


    public RocketMqUtil(RocketMQTemplate rocketMQTemplate) {
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
     * 异步通知发送
     *
     * @param topic        主题
     * @param message      消息
     * @param sendCallback 回调函数
     * @param <T>          消息类型
     */
    public <T> void asyncSend(String topic, Message<T> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback);
    }

    /**
     * 异步通知发送
     *
     * @param topic        主题
     * @param message      消息
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     * @param delayLevel   延迟级别
     * @param <T>          消息类型
     */
    public <T> void asyncSend(String topic, Message<T> message, SendCallback sendCallback, long timeout, int delayLevel) {
        rocketMQTemplate.asyncSend(topic, message, sendCallback, timeout, delayLevel);
    }

    /**
     * 异步批量通知发送
     *
     * @param topic        主题
     * @param messages     消息集合
     * @param sendCallback 回调函数
     */
    public <T extends Message<?>> void asyncSend(String topic, Collection<T> messages, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(topic, messages, sendCallback);
    }

    /**
     * 异步批量通知发送
     *
     * @param topic        主题
     * @param messages     消息集合
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     */
    public <T extends Message<?>> void asyncSend(String topic, Collection<T> messages, SendCallback sendCallback, long timeout) {
        rocketMQTemplate.asyncSend(topic, messages, sendCallback, timeout);
    }

    /**
     * 事务消息发送
     *
     * @param topic    主题
     * @param messages 消息
     * @param arg      参数
     */
    public <T> TransactionSendResult sendMessageInTransaction(String topic, Message<T> messages, Object arg) {
        return rocketMQTemplate.sendMessageInTransaction(topic, messages, arg);
    }

    /**
     * 延迟消息发送
     *
     * @param topic           主题
     * @param message         消息
     * @param delayTimeMillis 延迟时间
     * @param <T>             消息类型
     * @return SendResult
     */
    public <T> SendResult syncSendDelayTimeMillis(String topic, Message<T> message, long delayTimeMillis) {
        return rocketMQTemplate.syncSendDelayTimeMills(topic, message, delayTimeMillis);
    }

    /**
     * 定时消息发送
     *
     * @param topic            主题
     * @param message          消息
     * @param deliverTimeMills 时间戳
     * @param <T>              消息类型
     * @return SendResult
     */
    public <T> SendResult syncSendDeliverTimeMills(String topic, Message<T> message, long deliverTimeMills) {
        return rocketMQTemplate.syncSendDeliverTimeMills(topic, message, deliverTimeMills);
    }
}
