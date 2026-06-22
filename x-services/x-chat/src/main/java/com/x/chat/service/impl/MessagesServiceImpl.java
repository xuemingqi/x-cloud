package com.x.chat.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.x.chat.db.entity.Messages;
import com.x.chat.db.service.MessagesIService;
import com.x.chat.param.request.SendMessagesReq;
import com.x.chat.param.response.UserMessagesRes;
import com.x.chat.service.MessagesService;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.utils.ListUtil;
import com.x.framework.common.utils.ServerUtil;
import com.x.framework.common.exception.XException;
import com.x.api.contact.api.GroupApi;
import com.x.api.contact.param.response.GroupInfoRes;
import com.x.api.contact.param.response.GroupMembersRes;
import com.x.api.websocket.api.WebSocketApi;
import com.x.api.websocket.dto.MessageDto;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 16:09
 */
@Service
public class MessagesServiceImpl implements MessagesService {

    @Resource
    private MessagesIService messagesIService;

    @Resource
    private GroupApi groupApi;

    @Resource
    private Snowflake snowflake;

    @Resource
    private WebSocketApi webSocketApi;


    @Override
    public List<UserMessagesRes> getMessagesByGroupId(long groupId) {
        List<Messages> messages = messagesIService.lambdaQuery()
                .eq(Messages::getGroupId, groupId)
                .orderByDesc(Messages::getSendTime)
                .list();
        return ListUtil.copyList(messages, UserMessagesRes.class);
    }

    @Override
    public List<UserMessagesRes> getMessagesByUserId(long userId) {
        Long myId = ServerUtil.getAuthenticatedUserId();
        //发出去的消息
        List<Messages> sendMessages = messagesIService.lambdaQuery()
                .eq(Messages::getSenderId, myId)
                .eq(Messages::getReceiverId, userId)
                .orderByDesc(Messages::getSendTime)
                .list();

        //接收到的消息
        List<Messages> receiveMessages = messagesIService.lambdaQuery()
                .eq(Messages::getSenderId, userId)
                .eq(Messages::getReceiverId, myId)
                .orderByDesc(Messages::getSendTime)
                .list();

        //合并消息列表
        List<Messages> mergedList = Stream.of(sendMessages, receiveMessages)
                .flatMap(List::stream)
                .sorted((Comparator.comparing(Messages::getSendTime)))
                .toList();
        return ListUtil.copyList(mergedList, UserMessagesRes.class);
    }

    @Override
    public void sendMessages(SendMessagesReq sendMessagesReq) {
        Long groupId = sendMessagesReq.getGroupId();
        if (groupId != null) {
            getAndCheckGroupInfo(groupId);
        }

        Long myId = ServerUtil.getAuthenticatedUserId();
        long messagesId = snowflake.nextId();

        //保存消息
        Messages messages = new Messages()
                .setMessagesId(messagesId)
                .setSenderId(myId)
                .setWithdrawn(false);
        BeanUtils.copyProperties(sendMessagesReq, messages);
        messagesIService.save(messages);

        //发送通知消息
        UserMessagesRes userMessages = new UserMessagesRes()
                .setMessagesId(messagesId)
                .setSenderId(myId)
                .setSendTime(LocalDateTime.now());
        BeanUtils.copyProperties(sendMessagesReq, userMessages);
        MessageDto<UserMessagesRes> messageDto = MessageDto.<UserMessagesRes>builder()
                .state(1).data(userMessages)
                .build();

        //TODO 群组可灵活创建
        webSocketApi.pubChannel(1L, messageDto);
    }

    /**
     * 发送消息到指定群组用户
     *
     * @param groupId    群组ID
     * @param messageDto 消息内容
     */
    private void sendMessageToGroup(Long groupId, MessageDto<UserMessagesRes> messageDto) {
        getAndCheckGroupInfo(groupId);

        BaseResult<List<GroupMembersRes>> result = groupApi.getGroupMembers(groupId);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        result.getData().forEach(member -> webSocketApi.pub(member.getUserId(), messageDto));
    }

    /**
     * 发送消息到指定用户
     *
     * @param userId     用户ID
     * @param messageDto 消息内容
     */
    private void sendMessage(Long userId, MessageDto<UserMessagesRes> messageDto) {
        webSocketApi.pub(userId, messageDto);
    }

    /**
     * 获取群组信息
     *
     * @param groupId 群组ID
     * @return 群组信息
     */
    private GroupInfoRes getAndCheckGroupInfo(Long groupId) {
        BaseResult<GroupInfoRes> groupInfo = groupApi.getGroupInfo(groupId);
        if (!ResponseCodeEnum.SUCCESS.getCode().equals(groupInfo.getCode())) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }
        return groupInfo.getData();
    }
}
