package com.x.work.db.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : xuemingqi
 * @since : 2023/2/24 10:04
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    UNKNOWN(0, "UNKNOWN"), MD(1, "markdown"), DOC(2, "word");

    @EnumValue
    private final int code;

    private final String desc;
}
