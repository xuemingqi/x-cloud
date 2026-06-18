package com.x.gateway.filter;

import com.x.common.constants.CommonConstant;
import com.x.common.utils.IdUtil;
import com.x.common.utils.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.util.StringUtils;

/**
 * @author xuemingqi
 */
@Slf4j
@Configuration
public class GatewayFilter {
    @Bean
    public GlobalFilter globalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String traceId = request.getHeaders().getFirst(CommonConstant.TRACE_ID);
            if (!StringUtils.hasText(traceId)) {
                traceId = IdUtil.randomAlphanumeric(10);
            }
            ServerHttpRequest host = request.mutate().header(CommonConstant.TRACE_ID, traceId).build();
            exchange.getResponse().getHeaders().add(CommonConstant.TRACE_ID, traceId);
            ServerWebExchange build = exchange.mutate().request(host).build();
            String ip = ServletUtil.getRemoteIP(host.getHeaders());
            if (ip == null) {
                ip = host.getRemoteAddress() != null ? host.getRemoteAddress().getAddress().getHostAddress() : "unknown";
            }
            log.info("traceId:[{}] ip:[{}] uri:[{}]", traceId, ip, host.getURI());
            return chain.filter(build);
        };
    }
}
