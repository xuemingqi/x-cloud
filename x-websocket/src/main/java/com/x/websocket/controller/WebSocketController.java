package com.x.websocket.controller;

import com.x.websocket.api.WebSocketApi;
import com.x.websocket.dto.MessageDto;
import com.x.websocket.server.WebSocketServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : xuemingqi
 * @since : 2023/3/3 15:56
 */
@Slf4j
@Tag(name = "websocket", description = "websocket接口")
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WebSocketController implements WebSocketApi {

    private final WebSocketServer webSocketServer;

    @Override
    @Operation(tags = "websocket", summary = "接收websocket消息接口", method = "/pub/{sid}", description = "接收websocket消息接口")
    @PostMapping("/pub/{sid}")
    public <T> void pub(@PathVariable Long sid, @RequestBody MessageDto<T> message) {
        webSocketServer.sendMessage(sid.toString(), message);
    }
}
