package com.x.websocket.server;

import com.x.common.utils.JsonUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : xuemingqi
 * @since : 2023/3/3 14:59
 */
@Slf4j
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    /**
     * sid -> 本机连接会话
     */
    private static final ConcurrentHashMap<String, Session> websocketMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session) {
        log.info("新增sid==>" + sid);
        websocketMap.put(sid, session);
    }

    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("移除的sid==>" + sid);
        websocketMap.remove(sid);
    }

    @OnError
    public void onError(Throwable throwable) {
        log.error(ExceptionUtils.getStackTrace(throwable));
    }

    @OnMessage
    public void onMessage(@PathParam("sid") String sid, String message) {
        log.info("收到来自sid:{},的消息==>{}", sid, JsonUtil.toJsonStrIfNotAlready(message));
        sendLocalMessage(sid, message);
    }

    public void sendLocalMessage(String sid, Object message) {
        log.info("发送消息到sid:{},消息==>{}", sid, JsonUtil.toJsonStrIfNotAlready(message));
        try {
            var session = websocketMap.get(sid);
            if (session != null && session.isOpen()) {
                session.getBasicRemote().sendText(JsonUtil.toJsonStrIfNotAlready(message));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
