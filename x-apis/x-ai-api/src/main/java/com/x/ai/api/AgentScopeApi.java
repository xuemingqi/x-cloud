package com.x.ai.api;

import com.x.ai.domain.AgentCapabilityDo;
import com.x.ai.domain.AgentResponseDo;
import com.x.ai.dto.AgentPermissionRequestDto;
import com.x.ai.dto.AgentRequestDto;
import com.x.framework.common.config.InternalInterceptor;
import com.x.framework.common.response.BaseResult;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@FeignClient(name = "x-ai", configuration = InternalInterceptor.class, contextId = "x-ai-agent.api")
@Retryable(retryFor = {Exception.class}, backoff = @Backoff(delay = 1000L, multiplier = 1))
public interface AgentScopeApi {

    @GetMapping("/ai/agent/capabilities")
    BaseResult<List<AgentCapabilityDo>> capabilities();

    @PostMapping("/ai/agent/run")
    BaseResult<AgentResponseDo> run(@RequestBody @Valid AgentRequestDto request);

    @PostMapping("/ai/agent/stream/run")
    Flux<BaseResult<AgentResponseDo>> streamRun(@RequestBody @Valid AgentRequestDto request);

    @PostMapping("/ai/agent/plan/enter")
    BaseResult<AgentResponseDo> enterPlanMode(@RequestBody @Valid AgentRequestDto request);

    @PostMapping("/ai/agent/plan/exit")
    BaseResult<AgentResponseDo> exitPlanMode(@RequestBody @Valid AgentRequestDto request);

    @PostMapping("/ai/agent/permission")
    BaseResult<AgentResponseDo> permission(@RequestBody @Valid AgentPermissionRequestDto request);
}
