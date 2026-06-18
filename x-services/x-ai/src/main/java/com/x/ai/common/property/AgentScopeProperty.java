package com.x.ai.common.property;

import com.x.ai.common.constants.AgentScopeConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Data
@ConfigurationProperties(prefix = "agentscope")
public class AgentScopeProperty {

    private Boolean enabled = true;

    private String agentName = AgentScopeConstant.DEFAULT_AGENT_NAME;

    private String provider = AgentScopeConstant.DEFAULT_PROVIDER;

    private String model = AgentScopeConstant.DEFAULT_MODEL;

    private String baseUrl = AgentScopeConstant.DEFAULT_BASE_URL;

    private String apiKey;

    private Boolean streamEnabled = true;

    private String systemPrompt = AgentScopeConstant.DEFAULT_SYSTEM_PROMPT;

    private String workspace = AgentScopeConstant.DEFAULT_WORKSPACE;

    private String defaultUserId = AgentScopeConstant.DEFAULT_USER_ID;

    private String defaultSessionId = AgentScopeConstant.DEFAULT_SESSION_ID;

    private String permissionMode = "default";

    private Integer maxIters = AgentScopeConstant.DEFAULT_MAX_ITERS;

    private Integer compactionTriggerMessages = AgentScopeConstant.DEFAULT_COMPACTION_TRIGGER_MESSAGES;

    private Integer compactionKeepMessages = AgentScopeConstant.DEFAULT_COMPACTION_KEEP_MESSAGES;

    private Boolean taskListEnabled = true;

    private Boolean planModeEnabled = true;

    private Boolean filesystemToolsEnabled = true;

    private Boolean shellToolEnabled = false;

    private Boolean memoryToolsEnabled = true;

    private Boolean memoryHooksEnabled = true;

    private Boolean workspaceContextEnabled = true;

    private Boolean atPathExpansionEnabled = true;

    private Boolean subagentsEnabled = true;

    private Boolean dynamicSubagentsEnabled = true;

    private Boolean dynamicSkillsEnabled = true;

    private Boolean defaultWorkspaceSkillsEnabled = true;

    private Boolean toolsConfigEnabled = true;

    private Boolean skillManageToolEnabled = false;

    private Boolean skillCuratorEnabled = false;

    private Boolean projectWritable = false;

    private Boolean inheritShellEnv = false;
}
