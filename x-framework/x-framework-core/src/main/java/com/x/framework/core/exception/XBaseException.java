package com.x.framework.core.exception;

import com.x.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author : xuemingqi
 * @since : 2024-10-08 13:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public abstract class XBaseException extends RuntimeException {

    private ResponseCodeEnum responseCodeEnum;

    private boolean isPrint;


    public XBaseException(ResponseCodeEnum responseCodeEnum, boolean isPrint) {
        super(responseCodeEnum.getMsg());
        this.responseCodeEnum = responseCodeEnum;
        this.isPrint = isPrint;
    }

    public XBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
