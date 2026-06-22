package com.x.api.contact.param.request;

import com.x.api.contact.constants.ValidMsgConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 09:58
 */
@Data
@Schema(name = "CreateGroupReq", description = "创建群组请求参数")
public class CreateGroupReq {

    @NotBlank(message = ValidMsgConstant.GROUP_NAME_NULL_ERROR)
    @Schema(name = "groupName", description = "群组名称")
    private String groupName;

    @Schema(name = "groupDescription", description = "群组描述")
    private String groupDescription;

    @Schema(name = "groupAnnouncement", description = "群公告")
    private String groupAnnouncement;
}
