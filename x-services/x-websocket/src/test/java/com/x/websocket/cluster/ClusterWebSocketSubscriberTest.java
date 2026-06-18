package com.x.websocket.cluster;

import com.x.websocket.server.WebSocketServer;
import com.x.websocket.server.WebsocketChannelServer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class ClusterWebSocketSubscriberTest {

    private final WebSocketServer webSocketServer = mock(WebSocketServer.class);
    private final WebsocketChannelServer websocketChannelServer = mock(WebsocketChannelServer.class);
    private final ClusterWebSocketSubscriber subscriber = new ClusterWebSocketSubscriber(
            null,
            webSocketServer,
            websocketChannelServer
    );

    @Test
    void userMessageIsDeliveredOnlyThroughLocalUserSession() {
        var event = new ClusterWebSocketMessage(
                ClusterWebSocketMessage.Type.USER,
                "1001",
                "{\"code\":0}"
        );

        subscriber.deliverLocal(event);

        verify(webSocketServer).sendLocalMessage("1001", "{\"code\":0}");
        verifyNoInteractions(websocketChannelServer);
    }

    @Test
    void channelMessageIsDeliveredOnlyThroughLocalChannelSessions() {
        var event = new ClusterWebSocketMessage(
                ClusterWebSocketMessage.Type.CHANNEL,
                "2001",
                "{\"code\":0}"
        );

        subscriber.deliverLocal(event);

        verify(websocketChannelServer).broadcastLocalChannel("2001", "{\"code\":0}");
        verifyNoInteractions(webSocketServer);
    }
}
