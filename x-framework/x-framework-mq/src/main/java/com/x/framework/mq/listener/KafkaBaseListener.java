package com.x.framework.mq.listener;

import cn.hutool.core.util.TypeUtil;
import com.x.common.message.BaseMessage;
import com.x.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author : xuemingqi
 * @since : 2024-09-14 17:45
 */
@Slf4j
public abstract class KafkaBaseListener<T extends BaseMessage> extends AbstractMqListener<T, String> {

    @KafkaListener(containerGroup = "#{__listener.getContainerGroup()}", topics = "#{__listener.getTopics()}")
    public void listen(String message) {
        try {
            log.info("receive msg:{}", message);
            proc(parseMessage(message));
            log.info("proc ok msg:{}", message);
        } catch (Exception e) {
            handleError(e, message);
        }
    }

    protected T parseMessage(String message) {
        final Class<T> clazz = getGenericClass();
        return JsonUtil.jsonToBean(message, clazz);
    }

    /**
     * 获取容器组
     *
     * @return 容器组
     */
    public String getContainerGroup() {
        return getGenericClass().getSimpleName();
    }

    /**
     * 获取主题
     *
     * @return 主题
     */
    public String getTopics() {
        return getGenericClass().getSimpleName();
    }

    /**
     * 获取泛型类
     *
     * @return 泛型类
     */
    private Class<T> getGenericClass() {
        return (Class<T>) TypeUtil.getTypeArgument(this.getClass());
    }
}
