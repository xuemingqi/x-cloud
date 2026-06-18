package com.x.websocket.cluster;

import com.x.framework.redis.util.RedisUtil;
import com.x.websocket.dto.MessageDto;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ClusterWebSocketPublisherTest {

    private final RedisUtil redisUtil = mock(RedisUtil.class);
    private final ClusterWebSocketPublisher publisher = new ClusterWebSocketPublisher(redisUtil);

    @Test
    void publishUserMessageUsesClusterTopic() {
        var message = new MessageDto<String>();

        publisher.publishUserMessage("1001", message);

        verify(redisUtil).publish(eq(ClusterWebSocketConstants.MESSAGE_TOPIC), argThat(event ->
                event instanceof ClusterWebSocketMessage clusterMessage
                        && clusterMessage.type() == ClusterWebSocketMessage.Type.USER
                        && "1001".equals(clusterMessage.targetId())
                        && clusterMessage.payloadJson() != null
        ));
    }

    @Test
    void publishChannelMessageUsesClusterTopic() {
        var message = new MessageDto<String>();

        publisher.publishChannelMessage("2001", message);

        verify(redisUtil).publish(eq(ClusterWebSocketConstants.MESSAGE_TOPIC), argThat(event ->
                event instanceof ClusterWebSocketMessage clusterMessage
                        && clusterMessage.type() == ClusterWebSocketMessage.Type.CHANNEL
                        && "2001".equals(clusterMessage.targetId())
                        && clusterMessage.payloadJson() != null
        ));
    }
}
