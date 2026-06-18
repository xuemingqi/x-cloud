package com.x.framework.core.exception;

import com.x.common.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : xuemingqi
 * @since : 2024-09-13 13:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XException extends XBaseException {

    public XException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum, false);
    }
}
