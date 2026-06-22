package com.x.api.contact.param.request;

import com.x.api.contact.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 11:00
 */
@Data
@Schema(name = "AddGroupMemberReq", description = "添加群组成员请求参数")
public class AddGroupMemberReq {

    @NotEmpty(message = ValidMsgConstant.GROUP_MEMBER_NULL_ERROR)
    @Schema(name = "memberIds", description = "成员id列表")
    private Long[] memberIds;
}
