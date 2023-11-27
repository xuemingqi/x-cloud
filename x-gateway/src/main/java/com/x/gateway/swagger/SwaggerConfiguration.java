package com.x.gateway.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author : xuemingqi
 * @since : 2023/11/23 17:29
 */
@Configuration
@EnableOpenApi
@ConditionalOnProperty(prefix = "swagger.docket", name = "enable", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).select().build();
    }
}
