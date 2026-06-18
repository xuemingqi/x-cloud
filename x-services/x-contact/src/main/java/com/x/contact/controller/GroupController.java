package com.x.contact.controller;

import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.contact.api.GroupApi;
import com.x.contact.constants.ValidMsgConstant;
import com.x.contact.param.request.AddGroupMemberReq;
import com.x.contact.param.request.CreateGroupReq;
import com.x.contact.param.request.UpdateGroupReq;
import com.x.contact.param.response.GroupInfoRes;
import com.x.contact.param.response.GroupMembersRes;
import com.x.contact.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/04 11:00
 */
@Tag(name = "Group", description = "群组相关")
@Validated
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class GroupController implements GroupApi {

    private final GroupService groupService;


    @Override
    @Operation(tags = "Group", summary = "创建群组", method = "/create", description = "创建群组接口")
    @PostMapping("/create")
    public BaseResult<Void> createGroup(@RequestBody @Valid CreateGroupReq req) {
        groupService.createGroup(req);
        return ResultUtil.buildResultSuccess();
    }


    @Override
    @Operation(tags = "Group", summary = "修改群组", method = "/{groupId}", description = "修改群组接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @PatchMapping("/{groupId}")
    public BaseResult<Void> updateGroup(@PathVariable(value = "groupId")
                                        @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                                        @RequestBody @Valid UpdateGroupReq req) {
        groupService.updateGroup(groupId, req);
        return ResultUtil.buildResultSuccess();
    }


    @Override
    @Operation(tags = "Group", summary = "删除群组", method = "/{groupId}", description = "删除群组接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @DeleteMapping("/{groupId}")
    public BaseResult<Void> deleteGroup(@PathVariable(value = "groupId")
                                        @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId) {
        groupService.deleteGroup(groupId);
        return ResultUtil.buildResultSuccess();
    }


    @Override
    @Operation(tags = "Group", summary = "添加群组成员", method = "/{groupId}/add/member", description = "添加群组成员接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @PostMapping("/{groupId}/add/member")
    public BaseResult<Void> addMember(@PathVariable(value = "groupId")
                                      @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                                      @RequestBody AddGroupMemberReq req) {
        groupService.addGroupMember(groupId, req.getMemberIds());
        return ResultUtil.buildResultSuccess();
    }


    @Override
    @Operation(tags = "Group", summary = "删除群组成员", method = "/{groupId}/delete/member", description = "删除群组成员接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true),
            @Parameter(name = "memberId", description = "成员id", in = ParameterIn.PATH, required = true)
    })
    @DeleteMapping("/{groupId}/delete/member/{memberId}")
    public BaseResult<Void> deleteMember(@PathVariable(value = "groupId")
                                         @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId,
                                         @PathVariable(value = "memberId")
                                         @NotNull(message = ValidMsgConstant.GROUP_MEMBER_NULL_ERROR) Long memberId) {
        groupService.deleteGroupMember(groupId, memberId);
        return ResultUtil.buildResultSuccess();
    }


    @Override
    @Operation(tags = "Group", summary = "获取群组成员", method = "/{groupId}/member", description = "获取群组成员接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/{groupId}/member")
    public BaseResult<List<GroupMembersRes>> getGroupMembers(@PathVariable(value = "groupId")
                                                             @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId) {
        List<GroupMembersRes> members = groupService.getGroupMembers(groupId);
        return ResultUtil.buildResultSuccess(members);
    }


    @Override
    @Operation(tags = "Group", summary = "获取群组信息", method = "/{groupId}", description = "获取群组信息接口")
    @Parameters({
            @Parameter(name = "groupId", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/{groupId}")
    public BaseResult<GroupInfoRes> getGroupInfo(@PathVariable(value = "groupId")
                                          @NotNull(message = ValidMsgConstant.GROUP_ID_NULL_ERROR) Long groupId) {
        GroupInfoRes groupInfo = groupService.getGroupInfo(groupId);
        return ResultUtil.buildResultSuccess(groupInfo);
    }
}
