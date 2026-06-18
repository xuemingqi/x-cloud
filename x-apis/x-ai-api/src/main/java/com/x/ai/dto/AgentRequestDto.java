package com.x.ai.dto;

import com.x.ai.enums.AgentCapabilityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@Accessors(chain = true)
@Schema(name = "AgentRequestDto", description = "Agent 请求实体")
public class AgentRequestDto {

    @NotBlank(message = "请求不能为空")
    @Schema(name = "request", description = "用户请求")
    private String request;

    @Schema(name = "userId", description = "用户id，不传使用默认值")
    private String userId;

    @Schema(name = "sessionId", description = "会话id，不传使用默认值")
    private String sessionId;

    @Schema(name = "capability", description = "AgentScope 能力")
    private AgentCapabilityEnum capability = AgentCapabilityEnum.BASIC_AGENT;

    @Schema(name = "context", description = "业务上下文")
    private Map<String, Object> context;
}
