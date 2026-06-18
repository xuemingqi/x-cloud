package com.x.ai.agent.tool;

import io.agentscope.core.tool.Tool;
import io.agentscope.core.tool.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Component
public class XAgentScopeTools {

    private final Map<String, String> facts = new ConcurrentHashMap<>();

    @Tool(name = "x_current_time", description = "获取 x-ai 服务当前时间。", readOnly = true)
    public String currentTime() {
        return LocalDateTime.now().toString();
    }

    @Tool(name = "x_save_fact", description = "保存一条会话事实，供后续记忆或 RAG 示例检索。")
    public String saveFact(@ToolParam(name = "key", description = "事实键") String key,
                           @ToolParam(name = "value", description = "事实内容") String value) {
        facts.put(key, value);
        return "已保存：" + key;
    }

    @Tool(name = "x_search_fact", description = "按关键词检索已保存的会话事实。", readOnly = true)
    public List<String> searchFact(@ToolParam(name = "keyword", description = "关键词") String keyword) {
        return facts.entrySet().stream()
                .filter(entry -> entry.getKey().contains(keyword) || entry.getValue().contains(keyword))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .toList();
    }

    @Tool(name = "x_route_capability", description = "根据能力名称返回推荐的 AgentScope 能力串联路径。", readOnly = true)
    public String routeCapability(@ToolParam(name = "capability", description = "能力名称") String capability) {
        return switch (capability) {
            case "RAG" -> "模型集成 -> 工具系统 -> RAG -> 结构化输出";
            case "PLANNING" -> "会话管理 -> 计划模式 -> 工具系统 -> 状态管理";
            case "SUBAGENTS" -> "Routing -> Subagents -> Supervisor -> Handoffs";
            case "CODING_WITH_AI" -> "工作区 -> 文件系统 -> 沙箱 -> 计划模式";
            default -> "基础智能体 -> 模型集成 -> 工具系统 -> 事件流";
        };
    }
}
