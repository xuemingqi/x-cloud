package com.x.config.swagger;

import com.x.common.constants.CommonConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : xuemingqi
 * @since : 2023/11/23 17:29
 */
@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

    @Resource
    private SwaggerProperty property;

    @Bean
    public Docket docket() {
        SwaggerProperty.Docket docket = property.getDocket();
        return new Docket(DocumentationType.OAS_30)
                .host(docket.getHost())
                .enable(docket.isEnable())
                .globalRequestParameters(Collections.singletonList(
                        new RequestParameterBuilder()
                                .name(CommonConstant.TOKEN)
                                .description("鉴权token")
                                .in(ParameterType.HEADER)
                                .build()
                ))
                .groupName(docket.getGroupName())
                .select().apis(RequestHandlerSelectors.basePackage("com.x"))
                .paths(PathSelectors.any()).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        SwaggerProperty.Docket.ApiInfo apiInfo = property.getDocket().getApiInfo();
        SwaggerProperty.Docket.ApiInfo.Contact contact = property.getDocket().getApiInfo().getContact();
        return new ApiInfoBuilder()
                .title(apiInfo.getTitle())
                .termsOfServiceUrl(apiInfo.getTermsOfServiceUrl())
                .license(apiInfo.getLicense())
                .licenseUrl(apiInfo.getLicenseUrl())
                .description(apiInfo.getDescription())
                .contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()))
                .version(apiInfo.getVersion())
                .build();
    }

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(@Nullable Object bean, @Nullable String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(Objects.requireNonNull(getHandlerMappings(bean)));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    if (field != null) {
                        field.setAccessible(true);
                    }
                    if (field != null) {
                        return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
                return null;
            }
        };
    }
}
