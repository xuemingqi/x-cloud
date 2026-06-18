package com.x.contact.param.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2025/04/25 13:56
 */
@Data
@Accessors(chain = true)
@Schema(name = "GroupInfoRes", description = "群组信息响应参数")
public class GroupInfoRes {


    @Schema(name = "groupId", description = "群组id")
    private Long groupId;

    @Schema(name = "groupName", description = "群组名称")
    private String groupName;

    @Schema(name = "groupDescription", description = "群组描述")
    private String groupDescription;

    @Schema(name = "groupAnnouncement", description = "群公告")
    private String groupAnnouncement;

    @Schema(name = "updateTime", description = "群组更新时间")
    private String updateTime;

    @Schema(name = "createTime", description = "群组创建时间")
    private String createTime;
}
