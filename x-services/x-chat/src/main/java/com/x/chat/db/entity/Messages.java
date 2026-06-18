package com.x.chat.db.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.x.chat.db.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 15:57
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("messages")
public class Messages {

    /**
     * 消息id
     */
    @TableId("messages_id")
    private Long messagesId;

    /**
     * 群组id
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 发送者id
     */
    @TableField("sender_id")
    private Long senderId;

    /**
     * 接收者id
     */
    @TableField("receiver_id")
    private Long receiverId;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 消息类型
     */
    @TableField("type")
    private MessageType type;

    /**
     * 是否被撤回
     */
    @TableField("is_withdrawn")
    private boolean isWithdrawn;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private LocalDateTime sendTime;
}
