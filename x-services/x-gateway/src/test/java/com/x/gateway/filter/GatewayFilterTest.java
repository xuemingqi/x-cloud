package com.x.gateway.filter;

import com.x.framework.common.constants.CommonConstant;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class GatewayFilterTest {

    @Test
    void globalFilterPropagatesTraceIdToDownstreamRequest() {
        GatewayFilter filter = new GatewayFilter();
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/work/test").build());
        AtomicReference<ServerWebExchange> downstreamExchange = new AtomicReference<>();

        GatewayFilterChain chain = downstream -> {
            downstreamExchange.set(downstream);
            return Mono.empty();
        };

        filter.globalFilter().filter(exchange, chain).block();

        String responseTraceId = exchange.getResponse().getHeaders().getFirst(CommonConstant.TRACE_ID);
        String downstreamTraceId = downstreamExchange.get()
                .getRequest()
                .getHeaders()
                .getFirst(CommonConstant.TRACE_ID);
        assertThat(downstreamTraceId).isEqualTo(responseTraceId);
    }

    @Test
    void globalFilterKeepsIncomingTraceId() {
        GatewayFilter filter = new GatewayFilter();
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/work/test")
                        .header(CommonConstant.TRACE_ID, "client-trace")
                        .build());
        AtomicReference<ServerWebExchange> downstreamExchange = new AtomicReference<>();

        GatewayFilterChain chain = downstream -> {
            downstreamExchange.set(downstream);
            return Mono.empty();
        };

        filter.globalFilter().filter(exchange, chain).block();

        assertThat(exchange.getResponse().getHeaders().getFirst(CommonConstant.TRACE_ID))
                .isEqualTo("client-trace");
        assertThat(downstreamExchange.get().getRequest().getHeaders().getFirst(CommonConstant.TRACE_ID))
                .isEqualTo("client-trace");
    }
}
