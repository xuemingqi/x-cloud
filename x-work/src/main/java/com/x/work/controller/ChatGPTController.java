package com.x.work.controller;

import com.x.common.response.BaseResult;
import com.x.work.service.ChatGPTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author : xuemingqi
 * @since : 2023/2/27 16:19
 */
@Slf4j
@Tag(name = "ChatGPT", description = "提供chatgpt相关能力的接口")
@RestController
@RequestMapping("/chatGPT")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @Operation(tags = "ChatGPT", summary = "获取chatGPT回答接口", method = "/ask/{msg}", description = "获取chatGPT回答接口")
    @GetMapping(value = "/ask/{msg}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<?> getMdFile(@PathVariable("msg") String msg) {
        return chatGPTService.getAnswers(msg);
    }

    @Operation(tags = "ChatGPT", summary = "获取chatGPT流式回答接口", method = "/ask/stream/{msg}", description = "获取chatGPT流式回答接口")
    @GetMapping(value = "/ask/stream/{msg}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BaseResult<?>> stream(@PathVariable("msg") String msg) {
        return chatGPTService.getStreamAnswers(msg);
    }
}
