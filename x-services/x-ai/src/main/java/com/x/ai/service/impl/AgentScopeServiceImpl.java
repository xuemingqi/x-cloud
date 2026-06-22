package com.x.ai.service.impl;

import com.x.ai.common.property.AgentScopeProperty;
import com.x.ai.domain.AgentCapabilityDo;
import com.x.ai.domain.AgentEventDo;
import com.x.ai.domain.AgentResponseDo;
import com.x.ai.dto.AgentPermissionRequestDto;
import com.x.ai.dto.AgentRequestDto;
import com.x.ai.enums.AgentCapabilityEnum;
import com.x.ai.service.AgentScopeService;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.event.AgentEvent;
import io.agentscope.core.event.AgentEventType;
import io.agentscope.core.event.AgentResultEvent;
import io.agentscope.core.event.TextBlockDeltaEvent;
import io.agentscope.core.event.ToolCallStartEvent;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.permission.PermissionMode;
import io.agentscope.harness.agent.HarnessAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class AgentScopeServiceImpl implements AgentScopeService {

    private final HarnessAgent harnessAgent;

    private final AgentScopeProperty agentScopeProperty;


    @Override
    public BaseResult<List<AgentCapabilityDo>> capabilities() {
        List<AgentCapabilityDo> capabilities = Arrays.stream(AgentCapabilityEnum.values())
                .map(capability -> new AgentCapabilityDo()
                        .setCapability(capability)
                        .setDesc(capability.getDesc())
                        .setEnabled(true))
                .toList();
        return ResultUtil.buildResultSuccess(capabilities);
    }

    @Override
    public BaseResult<AgentResponseDo> run(AgentRequestDto request) {
        RuntimeContext runtimeContext = runtimeContext(request);
        Msg result = harnessAgent.call(new UserMessage(prompt(request)), runtimeContext).block();
        return ResultUtil.buildResultSuccess(buildResponse(request, runtimeContext, result, List.of()));
    }

    @Override
    public Flux<BaseResult<AgentResponseDo>> streamRun(AgentRequestDto request) {
        RuntimeContext runtimeContext = runtimeContext(request);
        String taskId = UUID.randomUUID().toString();
        return harnessAgent.streamEvents(new UserMessage(prompt(request)), runtimeContext)
                .map(event -> {
                    AgentEventDo eventDo = convertEvent(event);
                    AgentResponseDo response = baseResponse(request, runtimeContext, taskId)
                            .setEvents(List.of(eventDo));
                    if (event instanceof TextBlockDeltaEvent textBlockDeltaEvent) {
                        response.setText(textBlockDeltaEvent.getDelta());
                    } else if (event instanceof AgentResultEvent agentResultEvent) {
                        response.setText(agentResultEvent.getResult().getTextContent());
                    }
                    return ResultUtil.buildResultSuccess(response);
                });
    }

    @Override
    public BaseResult<AgentResponseDo> enterPlanMode(AgentRequestDto request) {
        RuntimeContext runtimeContext = runtimeContext(request);
        harnessAgent.enterPlanMode(runtimeContext);
        return ResultUtil.buildResultSuccess(baseResponse(request, runtimeContext)
                .setText("已进入计划模式"));
    }

    @Override
    public BaseResult<AgentResponseDo> exitPlanMode(AgentRequestDto request) {
        RuntimeContext runtimeContext = runtimeContext(request);
        harnessAgent.exitPlanMode(runtimeContext);
        return ResultUtil.buildResultSuccess(baseResponse(request, runtimeContext)
                .setText("已退出计划模式"));
    }

    @Override
    public BaseResult<AgentResponseDo> permission(AgentPermissionRequestDto request) {
        String userId = valueOrDefault(request.getUserId(), agentScopeProperty.getDefaultUserId());
        String sessionId = valueOrDefault(request.getSessionId(), agentScopeProperty.getDefaultSessionId());
        PermissionMode mode = PermissionMode.fromString(request.getMode());
        harnessAgent.setPermissionMode(userId, sessionId, mode);
        AgentRequestDto agentRequest = new AgentRequestDto()
                .setUserId(userId)
                .setSessionId(sessionId)
                .setCapability(AgentCapabilityEnum.HUMAN_IN_THE_LOOP)
                .setRequest("切换权限模式");
        return ResultUtil.buildResultSuccess(baseResponse(agentRequest, runtimeContext(agentRequest))
                .setText("权限模式已切换为：" + mode.getValue()));
    }

    private RuntimeContext runtimeContext(AgentRequestDto request) {
        return RuntimeContext.builder()
                .userId(valueOrDefault(request.getUserId(), agentScopeProperty.getDefaultUserId()))
                .sessionId(valueOrDefault(request.getSessionId(), agentScopeProperty.getDefaultSessionId()))
                .build();
    }

    private AgentResponseDo buildResponse(AgentRequestDto request, RuntimeContext runtimeContext, Msg result,
                                          List<AgentEventDo> events) {
        AgentResponseDo response = baseResponse(request, runtimeContext)
                .setEvents(events)
                .setText(result == null ? "" : result.getTextContent());
        if (result != null && result.hasStructuredData()) {
            response.setStructuredData(result.getStructuredData(false));
        }
        return response;
    }

    private AgentResponseDo baseResponse(AgentRequestDto request, RuntimeContext runtimeContext) {
        return baseResponse(request, runtimeContext, UUID.randomUUID().toString());
    }

    private AgentResponseDo baseResponse(AgentRequestDto request, RuntimeContext runtimeContext, String taskId) {
        return new AgentResponseDo()
                .setTaskId(taskId)
                .setUserId(runtimeContext.getUserId())
                .setSessionId(runtimeContext.getSessionId())
                .setCapability(capability(request));
    }

    private AgentEventDo convertEvent(AgentEvent event) {
        String content = "";
        if (event.getType() == AgentEventType.TEXT_BLOCK_DELTA && event instanceof TextBlockDeltaEvent textEvent) {
            content = textEvent.getDelta();
        } else if (event.getType() == AgentEventType.TOOL_CALL_START && event instanceof ToolCallStartEvent toolEvent) {
            content = toolEvent.getToolCallName();
        } else if (event.getType() == AgentEventType.AGENT_RESULT && event instanceof AgentResultEvent resultEvent) {
            content = resultEvent.getResult().getTextContent();
        }
        return new AgentEventDo()
                .setType(event.getType().name())
                .setSource(event.getSource())
                .setContent(content);
    }

    private String prompt(AgentRequestDto request) {
        AgentCapabilityEnum capability = capability(request);
        return """
                请使用 AgentScope Java 的 %s 能力处理请求。
                要求：
                1. 如果需要外部信息，优先调用已注册工具。
                2. 给出最小但完整的结果。
                3. 如果这个能力适合与其他能力串联，请说明串联路径。

                用户请求：
                %s
                """.formatted(capability.getDesc(), request.getRequest());
    }

    private AgentCapabilityEnum capability(AgentRequestDto request) {
        return Objects.requireNonNullElse(request.getCapability(), AgentCapabilityEnum.BASIC_AGENT);
    }

    private String valueOrDefault(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
