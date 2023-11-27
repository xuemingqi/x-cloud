package com.x.gateway.swagger;

import com.x.common.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/11/24 18:01
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
@ConditionalOnBean(SwaggerConfiguration.class)
public class SwaggerProvider implements SwaggerResourcesProvider {
    //SWAGGER3默认的URL后缀
    public static final String SWAGGER3URL = "/v3/api-docs";
    public static final String SWAGGER_VERSION = "3.0";

    //网关路由
    @Resource
    private RouteLocator routeLocator;

    @Resource
    private GatewayProperties gatewayProperties;

    //聚合其他服务接口
    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resourceList = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> {
                                    String id = routeDefinition.getId().substring(0, routeDefinition.getId().indexOf("-"));
                                    String location = id + SWAGGER3URL + "?group=" + id;
                                    resourceList.add(swaggerResource(id, location));
                                }
                        ));
        log.info(JsonUtil.toJsonStr(resourceList));
        return resourceList;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(SWAGGER_VERSION);
        return swaggerResource;
    }
}
