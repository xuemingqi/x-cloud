package com.x.work.service.impl;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.work.common.conf.OpenAiTemplate;
import com.x.work.common.property.OpenAiProperty;
import com.x.work.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : xuemingqi
 * @since : 2023/2/27 13:25
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ChatGPTServiceImpl implements ChatGPTService {

    private final OpenAiTemplate openAiTemplate;

    private final OpenAiProperty openAiProperty;

    @Override
    public BaseResult<?> getAnswers(String questionStr) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(openAiProperty.getModel())
                .temperature(openAiProperty.getTemperature())
                .topP(openAiProperty.getTop())
                .n(1)
                .frequencyPenalty(openAiProperty.getFrequencyPenalty())
                .presencePenalty(openAiProperty.getPresencePenalty())
                .maxTokens(openAiProperty.getMaxTokens())
                .prompt(questionStr)
                .build();
        try {
            CompletionResult response = openAiTemplate.createCompletion(completionRequest);
            return ResultUtil.buildResultSuccess(response);
        } catch (Exception e) {
            return ResultUtil.buildResultSuccess(ResponseCodeEnum.OPENAI_ERROR);
        }
    }

    @Override
    public Flux<BaseResult<?>> getStreamAnswers(String questionStr) {
        List<String> str = Arrays.asList("message1", "message2", "message3", "message4", "end");
        AtomicInteger i = new AtomicInteger();
//        return Flux.fromIterable(str)
//                .delayElements(Duration.ofSeconds(20))
//                .takeWhile(message -> !"end".equals(message))
//                .map(ResultUtil::buildResultSuccess);
        return Flux.generate(sink -> {
                    String message = str.get(i.getAndIncrement());
                    if (!"end".equals(message)) {
                        sink.next(ResultUtil.buildResultSuccess(message));
                    } else {
                        sink.complete();
                    }
                }
        );
    }
}
