package com.x.chat.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;

/**
 * @author : xuemingqi
 * @since : 2025/02/14 13:34
 */
public interface SseService {

    /**
     * 添加sse连接
     *
     * @param id         连接id
     * @param sseEmitter sse连接
     */
    void put(String id, SseEmitter sseEmitter);

    /**
     * 获取sse连接
     *
     * @param id 连接id
     * @return sse连接
     */
    SseEmitter get(String id);

    /**
     * 移除sse连接
     *
     * @param id 连接id
     */
    void remove(String id);

    /**
     * 通知指定连接
     *
     * @param id  连接id
     * @param msg 通知消息
     */
    void notify(String id, String msg);

    /**
     * 批量通知
     *
     * @param ids 连接id集合
     * @param msg 通知消息
     */
    void batchNotify(Set<String> ids, String msg);

    /**
     * 通知所有连接
     *
     * @param msg 通知消息
     */
    void notifyAll(String msg);


}


