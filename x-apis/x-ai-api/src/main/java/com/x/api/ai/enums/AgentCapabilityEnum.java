package com.x.api.ai.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Getter
@RequiredArgsConstructor
public enum AgentCapabilityEnum {

    BASIC_AGENT("基础智能体"),
    MODEL_INTEGRATION("模型集成"),
    TOOL_SYSTEM("工具系统"),
    MCP("MCP"),
    AGENT_AS_TOOL("Agent as Tool"),
    AGENT_SKILL("Agent Skill"),
    RAG("RAG"),
    STREAMING("流式输出"),
    HOOK_SYSTEM("钩子系统"),
    HUMAN_IN_THE_LOOP("Human-in-the-Loop"),
    MEMORY_MANAGEMENT("记忆管理"),
    PLANNING("计划"),
    STATE_MANAGEMENT("状态管理"),
    SESSION_MANAGEMENT("会话管理"),
    MULTIMODAL("多模态"),
    STRUCTURED_OUTPUT("结构化输出"),
    OBSERVABILITY("可观测与调试"),
    AG_UI_PROTOCOL("AG-UI 协议"),
    A2A_PROTOCOL("A2A 协议"),
    MSG_HUB("MsgHub"),
    CODING_WITH_AI("使用 AI 编程"),
    ONLINE_TRAINING("在线训练"),
    MULTI_AGENT_OVERVIEW("多智能体概览"),
    PIPELINE("管道"),
    CUSTOM_WORKFLOW("自定义工作流"),
    ROUTING("Routing"),
    MULTI_AGENT_SKILLS("Skills"),
    SUBAGENTS("Subagents"),
    SUPERVISOR("Supervisor"),
    HANDOFFS("Handoffs"),
    MULTI_AGENT_DEBATE("多智能体辩论"),
    HARNESS_ARCHITECTURE("Harness 架构"),
    CONTEXT_COMPACTION("上下文压缩"),
    WORKSPACE("工作区"),
    FILESYSTEM("文件系统"),
    SANDBOX("沙箱"),
    HARNESS_SKILL("Harness 技能"),
    CHANNEL("Channel");

    private final String desc;
}
