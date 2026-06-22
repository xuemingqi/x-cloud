package com.x.framework.web.interceptor;

import com.x.framework.common.auth.AuthClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 17:30
 */
@Configuration
@RefreshScope
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class InterceptorConfig {

    @Bean
    public WebMvcConfigurer addInterceptors(AuthInterceptor authInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                Objects.requireNonNull(registry)
                        .addInterceptor(authInterceptor)
                        .addPathPatterns("/**")
                        .excludePathPatterns("/error")
                        //swagger
                        .excludePathPatterns("/swagger**/**", "/v3/**", "/webjars/**", "/api/**");
            }
        };
    }


    @Bean
    public AuthInterceptor authenticationInterceptor(AuthClient authClient) {
        return new AuthInterceptor(authClient);
    }
}
