package com.x.websocket.server;

import com.x.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
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
     * 每个客户端对象
     */
    private static final ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session) {
        log.info("新增sid==>" + sid);
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.session = session;
        websocketMap.put(sid, webSocketServer);
    }

    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("移除的sid==>" + sid);
        websocketMap.remove(sid);
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(@PathParam("sid") String sid, String message) {
        log.info("收到来自sid:{},的消息==>{}", sid, JsonUtil.toJsonStrIfNotAlready(message));
        sendMessage(sid, message);
    }

    public void sendMessage(String sid, Object message) {
        try {
            WebSocketServer webSocketServer = websocketMap.get(sid);
            if (null != webSocketServer) {
                webSocketServer.session.getBasicRemote().sendText(JsonUtil.toJsonStrIfNotAlready(message));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
