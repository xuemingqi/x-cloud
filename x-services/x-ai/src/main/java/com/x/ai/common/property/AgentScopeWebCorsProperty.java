package com.x.ai.common.property;

import com.x.ai.common.constants.AgentScopeConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : xuemingqi
 * @since : 2026-06-18 10:12
 */
@Data
@ConfigurationProperties(prefix = "agentscope.web-cors")
public class AgentScopeWebCorsProperty {

    private Boolean enabled = true;

    private String pathPattern = AgentScopeConstant.AGENT_SCOPE_API_PATH_PATTERN;

    private String[] allowedOriginPatterns = AgentScopeConstant.DEFAULT_WEB_CORS_ORIGIN_PATTERNS;

    private String[] allowedMethods = AgentScopeConstant.DEFAULT_WEB_CORS_METHODS;

    private String[] allowedHeaders = AgentScopeConstant.DEFAULT_WEB_CORS_HEADERS;

    private Boolean allowCredentials = false;

    private Long maxAge = AgentScopeConstant.DEFAULT_WEB_CORS_MAX_AGE;
}
