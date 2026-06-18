package com.x.work.mq.consumer;

import com.x.common.message.TestMessage;
import com.x.common.utils.FunUtil;
import com.x.framework.mq.listener.RocketMqBaseListener;
import com.x.work.mq.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/2/3 16:04
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@ConditionalOnProperty(name = "mq.type", havingValue = "rocketmq")
@RocketMQMessageListener(consumerGroup = "TestMessage", topic = "TestMessage")
public class TestRocketMqConsumer extends RocketMqBaseListener<TestMessage> {

    private final TestService testService;


    @Override
    protected void proc(TestMessage message) {
        FunUtil.wrap(testService::test).accept(message);
    }
}
