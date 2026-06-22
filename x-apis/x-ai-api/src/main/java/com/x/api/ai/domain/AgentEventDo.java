package com.x.api.ai.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@Accessors(chain = true)
@Schema(name = "AgentEventDo", description = "Agent 事件")
public class AgentEventDo {

    @Schema(name = "type", description = "事件类型")
    private String type;

    @Schema(name = "source", description = "事件来源")
    private String source;

    @Schema(name = "content", description = "事件内容")
    private String content;
}
