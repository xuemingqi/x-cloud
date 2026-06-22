package com.x.contact.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.utils.StringUtil;
import com.x.framework.common.exception.XException;
import com.x.contact.db.entity.Group;
import com.x.contact.db.entity.GroupMember;
import com.x.contact.db.entity.User;
import com.x.contact.db.enums.MemberType;
import com.x.contact.db.service.GroupIService;
import com.x.contact.db.service.GroupMemberIService;
import com.x.contact.db.service.UserIService;
import com.x.contact.param.request.CreateGroupReq;
import com.x.contact.param.request.UpdateGroupReq;
import com.x.contact.param.response.GroupInfoRes;
import com.x.contact.param.response.GroupMembersRes;
import com.x.contact.service.GroupService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 10:14
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Resource
    private GroupIService groupIService;

    @Resource
    private GroupMemberIService groupMemberIService;

    @Resource
    private UserIService userIService;

    @Resource
    private Snowflake snowflake;


    @Override
    public void createGroup(CreateGroupReq req) {
        Group group = new Group()
                .setGroupId(snowflake.nextId());
        BeanUtils.copyProperties(req, group);
        groupIService.save(group);
    }

    @Override
    public void updateGroup(Long groupId, UpdateGroupReq req) {
        if (notExist(groupId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        groupIService.lambdaUpdate()
                .eq(Group::getGroupId, groupId)
                .set(StringUtil.isNotBlank(req.getGroupName()), Group::getGroupName, req.getGroupName())
                .set(StringUtil.isNotBlank(req.getGroupDescription()), Group::getGroupDescription, req.getGroupDescription())
                .set(StringUtil.isNotBlank(req.getGroupAnnouncement()), Group::getGroupAnnouncement, req.getGroupAnnouncement())
                .update();
    }

    @Override
    public void deleteGroup(Long groupId) {
        if (notExist(groupId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        groupIService.lambdaUpdate()
                .eq(Group::getGroupId, groupId)
                .remove();
    }

    @Override
    public void addGroupMember(Long groupId, Long[] userIds) {
        if (notExist(groupId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        List<GroupMember> members = new ArrayList<>();
        for (Long userId : userIds) {
            GroupMember groupMember = new GroupMember()
                    .setId(snowflake.nextId())
                    .setGroupId(groupId)
                    .setUserId(userId)
                    .setType(MemberType.MEMBER);
            members.add(groupMember);
        }
        groupMemberIService.saveBatch(members);
    }

    @Override
    public void deleteGroupMember(Long groupId, Long memberId) {
        if (notExist(groupId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        if (notExistMember(groupId, memberId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_MEMBER_NOT_EXIST);
        }

        groupMemberIService.lambdaUpdate()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUserId, memberId)
                .remove();
    }

    @Override
    public List<GroupMembersRes> getGroupMembers(Long groupId) {
        if (notExist(groupId)) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        List<GroupMember> members = groupMemberIService.lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .list();
        if (members.isEmpty()) {
            return List.of();
        }

        List<Long> userIds = members.stream()
                .map(GroupMember::getUserId)
                .toList();
        List<User> users = userIService.lambdaQuery()
                .in(User::getId, userIds)
                .list();

        return members.stream()
                .map(member -> {
                    GroupMembersRes groupMember = new GroupMembersRes()
                            .setGroupId(member.getGroupId())
                            .setUserId(member.getUserId())
                            .setType(com.x.contact.enums.MemberType.valueOf(member.getType().name()))
                            .setJoinTime(member.getJoinTime());
                    users.stream()
                            .filter(user -> user.getId().equals(member.getUserId()))
                            .findFirst()
                            .ifPresent(user -> groupMember.setUserName(user.getName()));
                    return groupMember;
                }).toList();
    }

    @Override
    public GroupInfoRes getGroupInfo(Long groupId) {
        Group group = groupIService.getById(groupId);
        if (group == null) {
            throw new XException(ResponseCodeEnum.CHAT_GROUP_NOT_EXIST);
        }

        GroupInfoRes groupInfo = new GroupInfoRes();
        BeanUtils.copyProperties(group, groupInfo);
        return groupInfo;
    }

    /**
     * 判断群组是否存在
     *
     * @param groupId 群组id
     * @return true: 不存在，false: 存在
     */
    private boolean notExist(Long groupId) {
        return !groupIService.lambdaQuery()
                .eq(Group::getGroupId, groupId)
                .exists();
    }

    /**
     * 判断群组成员是否存在
     *
     * @param groupId 群组id
     * @param userId  用户id
     * @return true: 不存在，false: 存在
     */
    private boolean notExistMember(Long groupId, Long userId) {
        return !groupMemberIService.lambdaQuery()
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getUserId, userId)
                .exists();
    }
}
