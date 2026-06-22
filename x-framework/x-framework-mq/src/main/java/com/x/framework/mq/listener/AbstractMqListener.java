package com.x.framework.mq.listener;

import com.x.framework.common.message.BaseMessage;
import com.x.framework.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 15:36
 */
@Slf4j
public abstract class AbstractMqListener<T extends BaseMessage, K> {

    /**
     * 处理消息
     *
     * @param message 消息
     */
    protected abstract void proc(T message);

    /**
     * 序列化消息
     *
     * @param message 消息
     */
    protected abstract T parseMessage(K message);

    /**
     * 处理异常
     *
     * @param e       异常
     * @param message 消息
     */
    protected void handleError(Exception e, Object message) {
        log.error("proc error msg:{},error:{}", JsonUtil.toJsonStr(message), ExceptionUtils.getStackTrace(e));
    }
}
