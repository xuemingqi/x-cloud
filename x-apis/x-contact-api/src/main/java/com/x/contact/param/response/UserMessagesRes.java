package com.x.contact.param.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.x.contact.enums.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 17:51
 */
@Data
@Accessors(chain = true)
@Schema(name = "UserMessagesRes", description = "用户消息响应参数")
public class UserMessagesRes {

    @Schema(name = "messagesId", description = "消息id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long messagesId;

    @Schema(name = "senderId", description = "发送者id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    @Schema(name = "receiverId", description = "接收者id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long receiverId;

    @Schema(name = "content", description = "消息内容")
    private String content;

    @Schema(name = "type", description = "消息类型")
    private MessageType type;

    @Schema(name = "sendTime", description = "发送时间")
    private LocalDateTime sendTime;
}
