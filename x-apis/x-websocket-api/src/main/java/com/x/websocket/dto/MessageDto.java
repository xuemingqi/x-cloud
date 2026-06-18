package com.x.websocket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : xuemingqi
 * @since : 2023/3/3 16:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "MessageDto", description = "消息信息")
public class MessageDto<T> {

    @Schema(name = "state", description = "消息状态")
    private Integer state;

    @Schema(name = "data", description = "消息内容")
    private T data;
}
