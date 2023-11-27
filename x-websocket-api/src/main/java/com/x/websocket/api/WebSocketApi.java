package com.x.websocket.api;

import com.x.websocket.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author : xuemingqi
 * @since : 2023/3/3 16:45
 */
@FeignClient("x-websocket")
public interface WebSocketApi {

    @PostMapping("/websocket/pub/{sid}")
    <T> void pub(@PathVariable("sid") Long sid, @RequestBody MessageDto<T> message);
}
