package com.x.contact.fallback;

import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import com.x.contact.api.GroupApi;
import com.x.contact.param.request.AddGroupMemberReq;
import com.x.contact.param.request.CreateGroupReq;
import com.x.contact.param.request.UpdateGroupReq;
import com.x.contact.param.response.GroupInfoRes;
import com.x.contact.param.response.GroupMembersRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/25 13:59
 */
@Slf4j
@Component
public class GroupApiFallbackFactory implements FallbackFactory<GroupApi> {
    @Override
    public GroupApi create(Throwable cause) {
        return new GroupApi() {
            @Override
            public BaseResult<Void> createGroup(CreateGroupReq req) {
                log.info("create server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Void> updateGroup(Long groupId, UpdateGroupReq req) {
                log.info("update server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Void> deleteGroup(Long groupId) {
                log.info("delete group server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Void> addMember(Long groupId, AddGroupMemberReq req) {
                log.info("add member server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Void> deleteMember(Long groupId, Long memberId) {
                log.info("delete member server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<List<GroupMembersRes>> getGroupMembers(Long groupId) {
                log.info("get group members server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<GroupInfoRes> getGroupInfo(Long groupId) {
                log.info("get group info server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }
        };
    }
}
