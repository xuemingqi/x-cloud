package com.x.contact.db.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 16:01
 */
@Getter
@AllArgsConstructor
public enum MessageType {

    /**
     * 未知消息类型
     */
    UNKNOWN(0, "UNKNOWN"),

    /**
     * 文本消息
     */
    TEXT(1, "text"),

    /**
     * 图片消息
     */
    IMAGE(2, "image"),

    /**
     * 视频消息
     */
    VIDEO(3, "video"),

    /**
     * 音频消息
     */
    AUDIO(4, "audio"),

    /**
     * 文件消息
     */
    FILE(5, "file"),

    /**
     * 系统消息
     */
    SYSTEM(6, "system");

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;
}
