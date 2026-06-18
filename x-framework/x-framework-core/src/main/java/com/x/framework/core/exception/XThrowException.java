package com.x.framework.core.exception;

import com.x.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : xuemingqi
 * @since : 2024-10-08 13:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XThrowException extends XBaseException {

    public XThrowException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum, true);
    }
}
