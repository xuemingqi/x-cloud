package com.x.auth.common.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 15:57
 */
@RefreshScope
@Configuration
public class JwtConfig {

    /**
     * 加密字符串
     */
    public static String sign;

    /**
     * 签发机构
     */
    public static String iss;

    /**
     * 超时时间（分钟）
     */
    public static int expiresTime;

    @Value("${jwt.sign}")
    public void setSign(String sign) {
        JwtConfig.sign = sign;
    }

    @Value("${jwt.iss}")
    public void setIss(String iss) {
        JwtConfig.iss = iss;
    }

    @Value("${jwt.expiresTime}")
    public void setExpiresTime(int expiresTime) {
        JwtConfig.expiresTime = expiresTime;
    }
}
