package com.x.work.mq.consumer;

import com.x.common.message.TestMessage;
import com.x.common.utils.FunUtil;
import com.x.framework.mq.listener.KafkaBaseListener;
import com.x.work.mq.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 17:50
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@ConditionalOnProperty(name = "mq.type", havingValue = "kafka")
public class TestKafkaConsumer extends KafkaBaseListener<TestMessage> {

    private final TestService testService;


    @Override
    protected void proc(TestMessage message) {
        FunUtil.wrap(testService::test).accept(message);
    }
}
