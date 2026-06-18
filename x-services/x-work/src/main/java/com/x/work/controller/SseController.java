package com.x.work.controller;

import com.x.framework.web.annotation.PassToken;
import com.x.work.service.SseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author : xuemingqi
 * @since : 2025/02/14 13:30
 */
@Tag(name = "SSE", description = "sse相关接口")
@PassToken
@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class SseController {

    private final SseService sseService;


    @GetMapping("/connect/{id}")
    public SseEmitter connect(@PathVariable String id) {
        SseEmitter emitter = new SseEmitter(0L);
        sseService.put(id, emitter);
        return emitter;
    }
}
