package com.x.contact.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "Sex", description = "性别")
public enum Sex {

    UNKNOWN(0, "UNKNOWN"), MALE(1, "男性"), FEMALE(2, "女性");

    Sex(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    @JsonValue
    private final int code;

    private final String desc;
}
