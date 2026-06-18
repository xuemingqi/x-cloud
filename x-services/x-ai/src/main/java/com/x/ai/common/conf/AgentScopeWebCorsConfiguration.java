package com.x.ai.common.conf;

import com.x.ai.common.property.AgentScopeWebCorsProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : xuemingqi
 * @since : 2026-06-18 10:12
 */
@Configuration
@EnableConfigurationProperties(AgentScopeWebCorsProperty.class)
@ConditionalOnProperty(prefix = "agentscope.web-cors", name = "enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AgentScopeWebCorsConfiguration implements WebMvcConfigurer {

    private final AgentScopeWebCorsProperty property;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(property.getPathPattern())
                .allowedOriginPatterns(property.getAllowedOriginPatterns())
                .allowedMethods(property.getAllowedMethods())
                .allowedHeaders(property.getAllowedHeaders())
                .allowCredentials(Boolean.TRUE.equals(property.getAllowCredentials()))
                .maxAge(property.getMaxAge());
    }
}
