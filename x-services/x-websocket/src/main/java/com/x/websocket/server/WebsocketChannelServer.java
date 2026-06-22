package com.x.websocket.server;

import com.x.framework.common.utils.JsonUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : xuemingqi
 * @since : 2025/04/04 13:26
 */
@Slf4j
@Component
@ServerEndpoint("/channel/{channelId}")
public class WebsocketChannelServer {

    // 存储频道与对应的会话集合 (channelId -> Set<Session>)
    private static final ConcurrentHashMap<String, Set<Session>> channelSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("channelId") String channelId) {
        // 将新会话添加到对应频道的集合中
        channelSessions.computeIfAbsent(channelId, ignored -> ConcurrentHashMap.newKeySet())
                .add(session);
        log.info("新客户端连接至频道: {}, 当前订阅数: {}", channelId,
                channelSessions.get(channelId).size());
    }

    @OnClose
    public void onClose(Session session, @PathParam("channelId") String channelId) {
        // 从频道中移除关闭的会话
        Set<Session> sessions = channelSessions.get(channelId);
        if (sessions != null) {
            sessions.remove(session);
            log.info("客户端断开连接, 频道: {}, 剩余订阅数: {}", channelId, sessions.size());

            // 如果频道没有订阅者了，清理频道
            if (sessions.isEmpty()) {
                channelSessions.remove(channelId);
            }
        }
    }

    @OnError
    public void onError(Session session, @PathParam("channelId") String channelId, Throwable error) {
        log.error("WebSocket错误, 频道: {}, 错误: {}", channelId, error.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session,
                          @PathParam("channelId") String channelId) {
        log.info("收到来自频道 {} 的消息: {}", channelId, message);
        broadcastLocalChannel(channelId, message);
    }

    /**
     * 向指定频道广播消息
     * @param channelId 频道ID
     * @param message 消息内容
     */
    public void broadcastLocalChannel(String channelId, Object message) {
        Set<Session> sessions = channelSessions.get(channelId);
        if (sessions != null && !sessions.isEmpty()) {
            sessions.forEach(s -> {
                try {
                    if (s.isOpen()) {
                        s.getBasicRemote().sendText(JsonUtil.toJsonStrIfNotAlready(message));
                    }
                } catch (IOException e) {
                    log.error("向频道 {} 发送消息失败: {}", channelId, e.getMessage());
                }
            });
            log.info("向频道 {} 广播消息成功, 接收客户端数: {}", channelId, sessions.size());
        }
    }

    /**
     * 获取指定频道的订阅数
     * @param channelId 频道ID
     * @return 订阅该频道的客户端数量
     */
    public static int getChannelSubscriberCount(String channelId) {
        Set<Session> sessions = channelSessions.get(channelId);
        return sessions != null ? sessions.size() : 0;
    }
}
