package com.x.contact.service;


import com.x.contact.param.request.CreateGroupReq;
import com.x.contact.param.request.UpdateGroupReq;
import com.x.contact.param.response.GroupInfoRes;
import com.x.contact.param.response.GroupMembersRes;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 10:14
 */
public interface GroupService {

    /**
     * 创建群组
     *
     * @param req 创建群组请求参数
     */
    void createGroup(CreateGroupReq req);

    /**
     * 更新群组
     *
     * @param groupId 群组id
     * @param req     创建群组请求参数
     */
    void updateGroup(Long groupId, UpdateGroupReq req);

    /**
     * 删除群组
     *
     * @param groupId 群组id
     */
    void deleteGroup(Long groupId);

    /**
     * 添加群组成员
     *
     * @param groupId 群组id
     * @param userIds 用户id列表
     */
    void addGroupMember(Long groupId, Long[] userIds);

    /**
     * 删除群组成员
     *
     * @param groupId  群组id
     * @param memberId 用户id
     */
    void deleteGroupMember(Long groupId, Long memberId);

    /**
     * 获取群组成员
     *
     * @param groupId 群组id
     * @return 群组成员列表
     */
    List<GroupMembersRes> getGroupMembers(Long groupId);

    /**
     * 获取群组信息
     *
     * @param groupId 群组id
     * @return 群组信息
     */
    GroupInfoRes getGroupInfo(Long groupId);
}
