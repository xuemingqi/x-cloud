package com.x.websocket.cluster;

/**
 * @author : xuemingqi
 * @since : 2026/6/12
 */
public record ClusterWebSocketMessage(Type type, String targetId, String payloadJson) {

    public enum Type {
        USER,
        CHANNEL
    }
}
