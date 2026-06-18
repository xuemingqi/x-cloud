package com.x.work.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 16:48
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "wx")
public class WeChatProperty {

    private String appId;

    private String secret;

    private String domain;

    private String redirectUrl;

}
