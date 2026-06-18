package com.x.framework.mq.listener;

import cn.hutool.core.util.TypeUtil;
import com.x.common.message.BaseMessage;
import com.x.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 15:37
 */
@Slf4j
public abstract class RocketMqBaseListener<T extends BaseMessage> extends AbstractMqListener<T, String> implements RocketMQListener<Message> {

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody());
            log.info("receive msg={}", body);
            T value = parseMessage(body);
            proc(value);
            log.info("proc ok msg={}", JsonUtil.toJsonStr(value));
        } catch (Exception e) {
            handleError(e, message);
        }
    }

    @Override
    protected T parseMessage(String body) {
        final Class<T> clazz = (Class<T>) TypeUtil.getTypeArgument(this.getClass());
        return JsonUtil.jsonToBean(body, clazz);
    }
}
