package com.x.websocket.cluster;

import com.x.framework.common.utils.JsonUtil;
import com.x.framework.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author : xuemingqi
 * @since : 2026/6/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClusterWebSocketPublisher {

    private final RedisUtil redisUtil;


    public void publishUserMessage(String sid, Object message) {
        publish(ClusterWebSocketMessage.Type.USER, sid, message);
    }

    public void publishChannelMessage(String channelId, Object message) {
        publish(ClusterWebSocketMessage.Type.CHANNEL, channelId, message);
    }

    private void publish(ClusterWebSocketMessage.Type type, String targetId, Object message) {
        var event = new ClusterWebSocketMessage(
                Objects.requireNonNull(type, "type must not be null"),
                Objects.requireNonNull(targetId, "targetId must not be null"),
                JsonUtil.toJsonStrIfNotAlready(message)
        );
        var subscriberCount = redisUtil.publish(ClusterWebSocketConstants.MESSAGE_TOPIC, event);
        log.info("发布websocket集群消息, type:{}, targetId:{}, subscribers:{}", type, targetId, subscriberCount);
    }
}
