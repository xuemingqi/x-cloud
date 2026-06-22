package com.x.api.ai.domain;

import com.x.api.ai.enums.AgentCapabilityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@Accessors(chain = true)
@Schema(name = "AgentResponseDo", description = "Agent 响应实体")
public class AgentResponseDo {

    @Schema(name = "taskId", description = "任务id")
    private String taskId;

    @Schema(name = "userId", description = "用户id")
    private String userId;

    @Schema(name = "sessionId", description = "会话id")
    private String sessionId;

    @Schema(name = "capability", description = "能力")
    private AgentCapabilityEnum capability;

    @Schema(name = "text", description = "文本结果")
    private String text;

    @Schema(name = "structuredData", description = "结构化结果")
    private Map<String, Object> structuredData;

    @Schema(name = "events", description = "事件列表")
    private List<AgentEventDo> events;
}
