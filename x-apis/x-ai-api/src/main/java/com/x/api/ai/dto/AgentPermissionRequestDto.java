package com.x.api.ai.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@Accessors(chain = true)
@Schema(name = "AgentPermissionRequestDto", description = "Agent 权限模式请求实体")
public class AgentPermissionRequestDto {

    @Schema(name = "userId", description = "用户id")
    private String userId;

    @Schema(name = "sessionId", description = "会话id")
    private String sessionId;

    @NotBlank(message = "权限模式不能为空")
    @Schema(name = "mode", description = "default/accept_edits/explore/bypass/dont_ask")
    private String mode;
}
