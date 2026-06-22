package com.x.api.contact.param.request;

import com.x.api.contact.enums.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 17:12
 */
@Data
@Schema(name = "SendMessagesReq", description = "发送消息请求参数")
public class SendMessagesReq {

    @Schema(name = "groupId", description = "群组id")
    private Long groupId;

    @Schema(name = "receiverId", description = "接收者id")
    private Long receiverId;

    @Schema(name = "content", description = "消息内容")
    private String content;

    @Schema(name = "type", description = "消息类型")
    private MessageType type;
}
