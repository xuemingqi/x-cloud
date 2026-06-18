package com.x.gateway.conf;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.ResultUtil;
import com.x.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author : xuemingqi
 * @since : 2024-10-11 11:27
 */
@Slf4j
@Component
public class SentinelBlockHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
        ResponseCodeEnum response = switch (t) {
            case FlowException flowException -> ResponseCodeEnum.FLOW_BLOCK;
            case DegradeException degradeException -> ResponseCodeEnum.DEGRADE_BLOCK;
            case ParamFlowException paramFlowException -> ResponseCodeEnum.PARAM_FLOW_BLOCK;
            case SystemBlockException systemBlockException -> ResponseCodeEnum.SYSTEM_BLOCK;
            case AuthorityException authorityException -> ResponseCodeEnum.AUTHORITY_BLOCK;
            default -> ResponseCodeEnum.SENTINEL_BLOCK;
        };
        return ServerResponse.status(response.getHttpStatus().value())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(JsonUtil.toJsonStr(ResultUtil.buildResultError(response)));
    }
}
