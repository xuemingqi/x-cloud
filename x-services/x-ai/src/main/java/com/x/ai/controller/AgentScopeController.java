package com.x.ai.controller;

import com.x.ai.api.AgentScopeApi;
import com.x.ai.domain.AgentCapabilityDo;
import com.x.ai.domain.AgentResponseDo;
import com.x.ai.dto.AgentPermissionRequestDto;
import com.x.ai.dto.AgentRequestDto;
import com.x.ai.service.AgentScopeService;
import com.x.common.constants.CommonConstant;
import com.x.common.response.BaseResult;
import com.x.framework.web.annotation.PassToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@PassToken
@Slf4j
@Tag(name = "AgentScope", description = "AgentScope Java 智能体")
@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AgentScopeController implements AgentScopeApi {

    private final AgentScopeService agentScopeService;


    @Override
    @Operation(tags = "AgentScope", summary = "AgentScope 能力列表", method = "/capabilities")
    @GetMapping("/capabilities")
    public BaseResult<List<AgentCapabilityDo>> capabilities() {
        return agentScopeService.capabilities();
    }

    @Override
    @Operation(tags = "AgentScope", summary = "执行 AgentScope 能力", method = "/run")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @PostMapping("/run")
    public BaseResult<AgentResponseDo> run(@RequestBody @Valid AgentRequestDto request) {
        return agentScopeService.run(request);
    }

    @Override
    @Operation(tags = "AgentScope", summary = "流式执行 AgentScope 能力", method = "/stream/run")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @PostMapping("/stream/run")
    public Flux<BaseResult<AgentResponseDo>> streamRun(@RequestBody @Valid AgentRequestDto request) {
        return agentScopeService.streamRun(request);
    }

    @Override
    @Operation(tags = "AgentScope", summary = "进入计划模式", method = "/plan/enter")
    @PostMapping("/plan/enter")
    public BaseResult<AgentResponseDo> enterPlanMode(@RequestBody @Valid AgentRequestDto request) {
        return agentScopeService.enterPlanMode(request);
    }

    @Override
    @Operation(tags = "AgentScope", summary = "退出计划模式", method = "/plan/exit")
    @PostMapping("/plan/exit")
    public BaseResult<AgentResponseDo> exitPlanMode(@RequestBody @Valid AgentRequestDto request) {
        return agentScopeService.exitPlanMode(request);
    }

    @Override
    @Operation(tags = "AgentScope", summary = "切换权限模式", method = "/permission")
    @PostMapping("/permission")
    public BaseResult<AgentResponseDo> permission(@RequestBody @Valid AgentPermissionRequestDto request) {
        return agentScopeService.permission(request);
    }
}
