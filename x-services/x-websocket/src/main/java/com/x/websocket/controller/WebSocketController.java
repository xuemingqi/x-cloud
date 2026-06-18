package com.x.websocket.controller;

import com.x.framework.web.annotation.PassToken;
import com.x.websocket.api.WebSocketApi;
import com.x.websocket.cluster.ClusterWebSocketPublisher;
import com.x.websocket.dto.MessageDto;
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
@PassToken
@Slf4j
@Tag(name = "websocket", description = "websocket接口")
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WebSocketController implements WebSocketApi {

    private final ClusterWebSocketPublisher clusterWebSocketPublisher;


    @Override
    @Operation(tags = "websocket", summary = "接收websocket消息接口", method = "/pub/{sid}", description = "接收websocket消息接口")
    @PostMapping("/pub/{sid}")
    public <T> void pub(@PathVariable Long sid, @RequestBody MessageDto<T> message) {
        clusterWebSocketPublisher.publishUserMessage(sid.toString(), message);
    }

    @Override
    @Operation(tags = "websocket", summary = "接收websocket消息接口", method = "/pub/{sid}", description = "接收websocket消息接口")
    @PostMapping("/channel/{sid}")
    public <T> void pubChannel(@PathVariable Long sid, @RequestBody MessageDto<T> message) {
        clusterWebSocketPublisher.publishChannelMessage(sid.toString(), message);
    }
}
