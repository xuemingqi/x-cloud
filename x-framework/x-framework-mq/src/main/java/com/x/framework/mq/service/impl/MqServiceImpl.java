package com.x.framework.mq.service.impl;

import com.x.framework.common.message.BaseMessage;
import com.x.framework.common.utils.JsonUtil;
import com.x.framework.mq.condition.OnRocketMqOrKafkaCondition;
import com.x.framework.mq.property.MqProperty;
import com.x.framework.mq.service.MqService;
import com.x.framework.mq.util.KafkaUtil;
import com.x.framework.mq.util.RocketMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 15:20
 */
@Slf4j
@Service
@Conditional(OnRocketMqOrKafkaCondition.class)
public class MqServiceImpl implements MqService {

    private final MqProperty mqProperty;

    private final RocketMqUtil rocketMqUtil;

    private final KafkaUtil kafkaUtil;

    public MqServiceImpl(MqProperty mqProperty,
                         @Autowired(required = false) RocketMqUtil rocketMqUtil,
                         @Autowired(required = false) KafkaUtil kafkaUtil) {
        this.mqProperty = mqProperty;
        this.rocketMqUtil = rocketMqUtil;
        this.kafkaUtil = kafkaUtil;
    }


    @Override
    public <T extends BaseMessage> void convertAndSend(T message) {
        switch (mqProperty.getType()) {
            case ROCKETMQ -> rocketMqUtil.convertAndSend(message.getTopic(), JsonUtil.toJsonStr(message));
            case KAFKA -> {
                try {
                    Optional.ofNullable(kafkaUtil.send(message.getTopic(), JsonUtil.toJsonStr(message)).get())
                            .orElseThrow(() -> new RuntimeException("kafka send message fail!"));
                } catch (Exception e) {
                    log.error("kafka send message fail! error:{}", ExceptionUtils.getStackTrace(e));
                    throw new RuntimeException("kafka send message fail!");
                }
            }
            default -> {
            }
        }
    }

    @Override
    public <T extends BaseMessage> SendResult syncSend(Collection<T> messages) {
        return rocketMqUtil.syncSend(getTopic(messages), messages.stream()
                .map(message -> MessageBuilder.withPayload(message).build())
                .toList());
    }

    @Override
    public <T extends BaseMessage> void asyncSend(T message, SendCallback sendCallback) {
        rocketMqUtil.asyncSend(message.getTopic(), MessageBuilder.withPayload(message).build(), sendCallback);
    }

    @Override
    public <T extends BaseMessage> void asyncSend(T message, SendCallback sendCallback, long timeout, int delayLevel) {
        rocketMqUtil.asyncSend(message.getTopic(), MessageBuilder.withPayload(message).build(), sendCallback, timeout, delayLevel);
    }

    @Override
    public <T extends BaseMessage> void asyncSend(Collection<T> messages, SendCallback sendCallback) {
        rocketMqUtil.asyncSend(getTopic(messages), messages.stream()
                .map(message -> MessageBuilder.withPayload(message).build())
                .toList(), sendCallback);
    }

    @Override
    public <T extends BaseMessage> void asyncSend(Collection<T> messages, SendCallback sendCallback, long timeout) {
        rocketMqUtil.asyncSend(getTopic(messages), messages.stream()
                .map(message -> MessageBuilder.withPayload(message).build())
                .toList(), sendCallback, timeout);
    }

    @Override
    public <T extends BaseMessage> TransactionSendResult sendMessageInTransaction(T message, Object arg) {
        return rocketMqUtil.sendMessageInTransaction(message.getTopic(), MessageBuilder.withPayload(message).build(), arg);
    }

    @Override
    public <T extends BaseMessage> SendResult syncSendDelayTimeMillis(T message, long delayTimeMillis) {
        return rocketMqUtil.syncSendDelayTimeMillis(message.getTopic(), MessageBuilder.withPayload(message).build(), delayTimeMillis);
    }

    @Override
    public <T extends BaseMessage> SendResult syncSendDeliverTimeMills(T message, long deliverTimeMills) {
        return rocketMqUtil.syncSendDeliverTimeMills(message.getTopic(), MessageBuilder.withPayload(message).build(), deliverTimeMills);
    }

    /**
     * 获取topic
     *
     * @param messages 消息集合
     * @return topic
     */
    private String getTopic(Collection<? extends BaseMessage> messages) {
        return messages.stream()
                .findAny()
                .map(BaseMessage::getTopic)
                .orElseThrow(() -> new RuntimeException("topic is empty"));
    }
}
