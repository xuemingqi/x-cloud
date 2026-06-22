package com.x.contact.api;

import com.x.framework.common.config.InternalInterceptor;
import com.x.framework.common.response.BaseResult;
import com.x.contact.constants.ValidMsgConstant;
import com.x.contact.fallback.GroupApiFallbackFactory;
import com.x.contact.param.request.AddGroupMemberReq;
import com.x.contact.param.request.CreateGroupReq;
import com.x.contact.param.request.UpdateGroupReq;
import com.x.contact.param.response.GroupInfoRes;
import com.x.contact.param.response.GroupMembersRes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/25 13:52
 */
@FeignClient(name = "x-contact",
        configuration = InternalInterceptor.class,
        fallbackFactory = GroupApiFallbackFactory.class,
        contextId = "x-contact-group-api")
public interface GroupApi {

    @PostMapping("/contact/group/create")
    BaseResult<Void> createGroup(@RequestBody @Valid CreateGroupReq req);


    @PatchMapping("/contact/group/{groupId}")
    BaseResult<Void> updateGroup(@PathVariable(value = "groupId")
                                 @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                                 @RequestBody @Valid UpdateGroupReq req);


    @DeleteMapping("/contact/group/{groupId}")
    BaseResult<Void> deleteGroup(@PathVariable(value = "groupId")
                                 @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId);


    @PostMapping("/contact/group/{groupId}/add/member")
    BaseResult<Void> addMember(@PathVariable(value = "groupId")
                               @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                               @RequestBody AddGroupMemberReq req);


    @DeleteMapping("/contact/group/{groupId}/delete/member/{memberId}")
    BaseResult<Void> deleteMember(@PathVariable(value = "groupId")
                                  @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                                  @PathVariable(value = "memberId")
                                  @NotNull(message = ValidMsgConstant.GROUP_MEMBER_NULL_ERROR) Long memberId);


    @GetMapping("/contact/group/{groupId}/member")
    BaseResult<List<GroupMembersRes>> getGroupMembers(@PathVariable(value = "groupId")
                                                      @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId);


    @GetMapping("/contact/group/{groupId}")
    BaseResult<GroupInfoRes> getGroupInfo(@PathVariable(value = "groupId")
                                          @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId);
}
