package com.x.websocket.cluster;

import com.x.framework.redis.util.RedisUtil;
import com.x.websocket.server.WebSocketServer;
import com.x.websocket.server.WebsocketChannelServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2026/6/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClusterWebSocketSubscriber implements InitializingBean {

    private final RedisUtil redisUtil;

    private final WebSocketServer webSocketServer;

    private final WebsocketChannelServer websocketChannelServer;


    @Override
    public void afterPropertiesSet() {
        redisUtil.subscribe(
                ClusterWebSocketConstants.MESSAGE_TOPIC,
                ClusterWebSocketMessage.class,
                (channel, event) -> deliverLocal(event)
        );
        log.info("订阅websocket集群消息, topic:{}", ClusterWebSocketConstants.MESSAGE_TOPIC);
    }

    void deliverLocal(ClusterWebSocketMessage event) {
        switch (event.type()) {
            case USER -> webSocketServer.sendLocalMessage(event.targetId(), event.payloadJson());
            case CHANNEL -> websocketChannelServer.broadcastLocalChannel(event.targetId(), event.payloadJson());
        }
    }
}
