package com.x.work.service;

import com.x.common.response.BaseResult;
import reactor.core.publisher.Flux;

/**
 * @author: xuemingqi
 * @since: 2023/2/27 13:24
 */
public interface ChatGPTService {

    /**
     * 获取chatGPT回答
     *
     * @param questionStr 问题
     * @return 回答
     */
    BaseResult<?> getAnswers(String questionStr);

    /**
     * 获取流式回答
     *
     * @param questionStr 问题
     * @return 回答
     */
    Flux<BaseResult<?>> getStreamAnswers(String questionStr);
}
