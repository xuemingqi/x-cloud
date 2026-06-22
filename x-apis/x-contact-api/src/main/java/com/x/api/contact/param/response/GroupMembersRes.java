package com.x.api.contact.param.response;

import com.x.api.contact.enums.MemberType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 13:07
 */
@Data
@Accessors(chain = true)
@Schema(name = "GroupMembersRes", description = "群组成员响应参数")
public class GroupMembersRes {

    @Schema(name = "groupId", description = "群组id")
    private Long groupId;

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "userName", description = "用户名")
    private String userName;

    @Schema(name = "type", description = "成员类型")
    private MemberType type;

    @Schema(name = "joinTime", description = "加入时间")
    private LocalDateTime joinTime;
}
