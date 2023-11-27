package com.x.work.mq.consumer;

import com.x.common.utils.JsonUtil;
import com.x.work.mq.constants.GroupConstant;
import com.x.work.mq.constants.TopicConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/2/3 16:04
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RocketMQMessageListener(consumerGroup = GroupConstant.GROUP_A, topic = TopicConstant.TOPIC_B)
public class TopicAConsumer implements RocketMQListener<Message> {

    @Override
    public void onMessage(Message message) {
        log.info(JsonUtil.toJsonStrIfNotAlready(message));
    }
}
