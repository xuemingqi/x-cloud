package com.x.framework.mq.service;

import com.x.framework.common.message.BaseMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;

import java.util.Collection;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 15:20
 */
public interface MqService {

    /**
     * 单条通知发送
     *
     * @param message 消息
     */
    <T extends BaseMessage> void convertAndSend(T message);

    /**
     * 批量通知发送
     *
     * @param messages 消息集合
     */
    <T extends BaseMessage> SendResult syncSend(Collection<T> messages);

    /**
     * 异步通知发送
     *
     * @param message      消息
     * @param sendCallback 回调函数
     * @param <T>          消息类型
     */
    <T extends BaseMessage> void asyncSend(T message, SendCallback sendCallback);

    /**
     * 异步通知发送
     *
     * @param message      消息
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     * @param delayLevel   延迟级别
     * @param <T>          消息类型
     */
    <T extends BaseMessage> void asyncSend(T message, SendCallback sendCallback, long timeout, int delayLevel);

    /**
     * 异步批量通知发送
     *
     * @param messages     消息集合
     * @param sendCallback 回调函数
     */
    <T extends BaseMessage> void asyncSend(Collection<T> messages, SendCallback sendCallback);

    /**
     * 异步批量通知发送
     *
     * @param messages     消息集合
     * @param sendCallback 回调函数
     * @param timeout      超时时间
     */
    <T extends BaseMessage> void asyncSend(Collection<T> messages, SendCallback sendCallback, long timeout);

    /**
     * 事务消息发送
     *
     * @param message 消息
     * @param arg     参数
     */
    <T extends BaseMessage> TransactionSendResult sendMessageInTransaction(T message, Object arg);

    /**
     * 延迟消息发送
     *
     * @param message         消息
     * @param delayTimeMillis 延迟时间
     * @param <T>             消息类型
     * @return SendResult
     */
    <T extends BaseMessage> SendResult syncSendDelayTimeMillis(T message, long delayTimeMillis);

    /**
     * 定时消息发送
     *
     * @param message          消息
     * @param deliverTimeMills 时间戳
     * @param <T>              消息类型
     * @return SendResult
     */
    <T extends BaseMessage> SendResult syncSendDeliverTimeMills(T message, long deliverTimeMills);
}
