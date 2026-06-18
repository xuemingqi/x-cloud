package com.x.ai.agent.conf;

import com.x.ai.agent.tool.XAgentScopeTools;
import com.x.ai.common.constants.AgentScopeConstant;
import com.x.ai.common.property.AgentScopeProperty;
import io.agentscope.core.formatter.openai.DeepSeekFormatter;
import io.agentscope.core.model.Model;
import io.agentscope.core.model.ModelRegistry;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.permission.PermissionContextState;
import io.agentscope.core.permission.PermissionMode;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.harness.agent.HarnessAgent;
import io.agentscope.harness.agent.filesystem.spec.LocalFilesystemSpec;
import io.agentscope.harness.agent.memory.compaction.CompactionConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Configuration
@EnableConfigurationProperties(AgentScopeProperty.class)
@ConditionalOnProperty(prefix = "agentscope", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AgentScopeConfiguration {

    @Bean
    public Toolkit agentScopeToolkit(XAgentScopeTools xAgentScopeTools) {
        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(xAgentScopeTools);
        return toolkit;
    }

    @Bean
    public HarnessAgent harnessAgent(AgentScopeProperty property, Toolkit agentScopeToolkit) {
        HarnessAgent.Builder builder = HarnessAgent.builder()
                .name(property.getAgentName())
                .sysPrompt(property.getSystemPrompt())
                .model(agentScopeModel(property))
                .toolkit(agentScopeToolkit)
                .maxIters(property.getMaxIters())
                .defaultSessionId(property.getDefaultSessionId())
                .permissionContext(PermissionContextState.builder()
                        .mode(PermissionMode.fromString(property.getPermissionMode()))
                        .build())
                .workspace(Paths.get(property.getWorkspace()))
                .filesystem(new LocalFilesystemSpec()
                        .inheritEnv(Boolean.TRUE.equals(property.getInheritShellEnv()))
                        .project(Paths.get(System.getProperty("user.dir")))
                        .projectWritable(Boolean.TRUE.equals(property.getProjectWritable())))
                .compaction(CompactionConfig.builder()
                        .triggerMessages(property.getCompactionTriggerMessages())
                        .keepMessages(property.getCompactionKeepMessages())
                        .build());

        if (Boolean.TRUE.equals(property.getTaskListEnabled())) {
            builder.enableTaskList();
        }
        if (Boolean.TRUE.equals(property.getPlanModeEnabled())) {
            builder.enablePlanMode();
        }
        if (!Boolean.TRUE.equals(property.getFilesystemToolsEnabled())) {
            builder.disableFilesystemTools();
        }
        if (!Boolean.TRUE.equals(property.getShellToolEnabled())) {
            builder.disableShellTool();
        }
        if (!Boolean.TRUE.equals(property.getMemoryToolsEnabled())) {
            builder.disableMemoryTools();
        }
        if (!Boolean.TRUE.equals(property.getMemoryHooksEnabled())) {
            builder.disableMemoryHooks();
        }
        if (!Boolean.TRUE.equals(property.getWorkspaceContextEnabled())) {
            builder.disableWorkspaceContext();
        }
        if (!Boolean.TRUE.equals(property.getAtPathExpansionEnabled())) {
            builder.disableAtPathExpansion();
        }
        if (!Boolean.TRUE.equals(property.getSubagentsEnabled())) {
            builder.disableSubagents();
        }
        if (!Boolean.TRUE.equals(property.getDynamicSubagentsEnabled())) {
            builder.disableDynamicSubagents();
        }
        if (!Boolean.TRUE.equals(property.getDynamicSkillsEnabled())) {
            builder.disableDynamicSkills();
        }
        if (!Boolean.TRUE.equals(property.getDefaultWorkspaceSkillsEnabled())) {
            builder.disableDefaultWorkspaceSkills();
        }
        if (!Boolean.TRUE.equals(property.getToolsConfigEnabled())) {
            builder.disableToolsConfig();
        }
        if (Boolean.TRUE.equals(property.getSkillManageToolEnabled())) {
            builder.enableSkillManageTool(false);
        }
        if (Boolean.TRUE.equals(property.getSkillCuratorEnabled())) {
            builder.enableSkillCurator(null);
        }
        return builder.build();
    }

    @Bean
    public Model agentScopeModel(AgentScopeProperty property) {
        if (AgentScopeConstant.PROVIDER_DEEPSEEK.equalsIgnoreCase(property.getProvider())) {
            return OpenAIChatModel.builder()
                    .apiKey(resolveDeepSeekApiKey(property))
                    .baseUrl(property.getBaseUrl())
                    .modelName(property.getModel())
                    .stream(Boolean.TRUE.equals(property.getStreamEnabled()))
                    .formatter(new DeepSeekFormatter())
                    .build();
        }
        return ModelRegistry.resolve(property.getModel());
    }

    private String resolveDeepSeekApiKey(AgentScopeProperty property) {
        if (StringUtils.hasText(property.getApiKey())) {
            return property.getApiKey();
        }
        return System.getenv(AgentScopeConstant.DEEPSEEK_API_KEY_ENV);
    }
}
