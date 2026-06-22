package com.x.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 操作类型
 */
@Getter
@AllArgsConstructor
public enum OperationEnum {

    /**
     * 读
     */
    READ,

    /**
     * 写
     */
    WRITE
}
