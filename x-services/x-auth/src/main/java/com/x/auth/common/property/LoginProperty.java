package com.x.auth.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 15:15
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "login")
public class LoginProperty {

    /**
     * 失败次数
     */
    private String failTimes;

    /**
     * 失败周期时间（分钟）
     */
    private Integer cycle;

    /**
     * 锁定时间（分钟）
     */
    private Integer lockTime;
}
