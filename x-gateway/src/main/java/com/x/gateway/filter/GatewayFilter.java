package com.x.gateway.filter;

import cn.hutool.core.util.IdUtil;
import com.x.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author xuemingqi
 */
@Slf4j
@Configuration
public class GatewayFilter {
    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            String traceId = IdUtil.nanoId(10);
            exchange.getRequest().mutate().header(CommonConstant.TRACE_ID, traceId);
            exchange.getResponse().getHeaders().add(CommonConstant.TRACE_ID, traceId);
            ServerHttpRequest host = exchange.getRequest();
            ServerWebExchange build = exchange.mutate().request(host).build();
            return chain.filter(build);
        };
    }
}
