package com.x.ai.common.constants;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
public final class AgentScopeConstant {

    public static final String DEFAULT_AGENT_NAME = "x-agent";

    public static final String PROVIDER_DEEPSEEK = "deepseek";

    public static final String DEFAULT_PROVIDER = PROVIDER_DEEPSEEK;

    public static final String DEFAULT_MODEL = "deepseek-v4-pro";

    public static final String DEFAULT_BASE_URL = "https://api.deepseek.com";

    public static final String DEEPSEEK_API_KEY_ENV = "DEEPSEEK_API_KEY";

    public static final String AGENT_SCOPE_API_PATH_PATTERN = "/ai/agent/**";

    public static final String[] DEFAULT_WEB_CORS_ORIGIN_PATTERNS = {
            "http://127.0.0.1:*",
            "http://localhost:*"
    };

    public static final String[] DEFAULT_WEB_CORS_METHODS = {
            "GET",
            "POST",
            "OPTIONS"
    };

    public static final String[] DEFAULT_WEB_CORS_HEADERS = {
            "*"
    };

    public static final long DEFAULT_WEB_CORS_MAX_AGE = 3600L;

    public static final String DEFAULT_USER_ID = "anonymous";

    public static final String DEFAULT_SESSION_ID = "default-session";

    public static final String DEFAULT_WORKSPACE = ".agentscope/x-ai";

    public static final int DEFAULT_MAX_ITERS = 10;

    public static final int DEFAULT_COMPACTION_TRIGGER_MESSAGES = 30;

    public static final int DEFAULT_COMPACTION_KEEP_MESSAGES = 10;

    public static final String DEFAULT_SYSTEM_PROMPT = """
            你是 x-cloud 的 AgentScope Java 智能体，负责把用户请求拆解、调用工具、维护记忆，并在需要时给出结构化结果。
            回答时优先使用中文，保持简洁、准确、可执行。
            """;

    private AgentScopeConstant() {
    }
}
