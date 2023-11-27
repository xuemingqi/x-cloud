package com.x.config.interceptor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nullable;
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
    public WebMvcConfigurer addInterceptors() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(@Nullable InterceptorRegistry registry) {
                Objects.requireNonNull(registry).addInterceptor(authenticationInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/error")
                        //swagger
                        .excludePathPatterns("/swagger**/**", "/v3/**", "/webjars/**", "/api/**");
            }
        };
    }


    @Bean
    public AuthInterceptor authenticationInterceptor() {
        return new AuthInterceptor();
    }
}
