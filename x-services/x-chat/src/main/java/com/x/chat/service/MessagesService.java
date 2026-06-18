package com.x.chat.service;

import com.x.chat.param.request.SendMessagesReq;
import com.x.chat.param.response.UserMessagesRes;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 16:09
 */
public interface MessagesService {

    /**
     * 根据群组ID获取消息列表
     *
     * @param groupId 群组ID
     * @return 消息列表
     */
    List<UserMessagesRes> getMessagesByGroupId(long groupId);

    /**
     * 根据用户ID获取消息列表
     *
     * @param userId 用户ID
     * @return 消息列表
     */
    List<UserMessagesRes> getMessagesByUserId(long userId);

    /**
     * 发送消息
     *
     * @param sendMessagesReq 发送消息请求参数
     */
    void sendMessages(SendMessagesReq sendMessagesReq);
}
