package com.x.work.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/2/27 13:52
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperty {

    private String token;

    private String model;

    private Integer maxTokens;

    private Double temperature;

    private Double top;

    private Integer timeOut;

    private Boolean echo;

    private Double frequencyPenalty;

    private Double presencePenalty;
}
