package com.x.contact.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 11:11
 */
@Getter
@AllArgsConstructor
public enum MemberType {

    UNKNOWN(0, "UNKNOWN"),

    OPERATOR(1, "创建者"),

    ADMIN(2, "管理员"),

    MEMBER(3, "群成员");

    @JsonValue
    private final int code;

    private final String desc;
}
