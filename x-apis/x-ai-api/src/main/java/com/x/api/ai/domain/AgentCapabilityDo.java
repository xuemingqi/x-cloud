package com.x.api.ai.domain;

import com.x.api.ai.enums.AgentCapabilityEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@Accessors(chain = true)
@Schema(name = "AgentCapabilityDo", description = "Agent 能力")
public class AgentCapabilityDo {

    @Schema(name = "capability", description = "能力编码")
    private AgentCapabilityEnum capability;

    @Schema(name = "desc", description = "能力描述")
    private String desc;

    @Schema(name = "enabled", description = "是否启用")
    private Boolean enabled;
}
