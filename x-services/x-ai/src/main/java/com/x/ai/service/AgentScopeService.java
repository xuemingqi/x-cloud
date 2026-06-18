package com.x.ai.service;

import com.x.ai.domain.AgentCapabilityDo;
import com.x.ai.domain.AgentResponseDo;
import com.x.ai.dto.AgentPermissionRequestDto;
import com.x.ai.dto.AgentRequestDto;
import com.x.common.response.BaseResult;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2026-06-17 16:10
 */
public interface AgentScopeService {

    /**
     * 查询 AgentScope 当前支持的能力列表。
     *
     * @return 能力列表
     */
    BaseResult<List<AgentCapabilityDo>> capabilities();

    /**
     * 同步执行一次 AgentScope 智能体任务。
     *
     * @param request 智能体请求
     * @return 智能体执行结果
     */
    BaseResult<AgentResponseDo> run(AgentRequestDto request);

    /**
     * 流式执行一次 AgentScope 智能体任务。
     *
     * @param request 智能体请求
     * @return 智能体事件与文本增量流
     */
    Flux<BaseResult<AgentResponseDo>> streamRun(AgentRequestDto request);

    /**
     * 进入 AgentScope 计划模式。
     *
     * @param request 计划模式上下文请求
     * @return 计划模式切换结果
     */
    BaseResult<AgentResponseDo> enterPlanMode(AgentRequestDto request);

    /**
     * 退出 AgentScope 计划模式。
     *
     * @param request 计划模式上下文请求
     * @return 计划模式切换结果
     */
    BaseResult<AgentResponseDo> exitPlanMode(AgentRequestDto request);

    /**
     * 切换 AgentScope 权限模式。
     *
     * @param request 权限模式请求
     * @return 权限模式切换结果
     */
    BaseResult<AgentResponseDo> permission(AgentPermissionRequestDto request);
}
