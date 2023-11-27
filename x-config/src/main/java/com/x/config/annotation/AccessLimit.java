package com.x.config.annotation;


import com.x.common.enums.OperationEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {
    /**
     * 每秒内API请求次数
     */
    int counts() default 5;

    /**
     * 操作类型
     */
    OperationEnum types() default OperationEnum.READ;
}
