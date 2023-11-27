package com.x.config.exception.handler;

import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 通用异常处理类
 *
 * @author xuemingqi
 */
@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus
    public BaseResult<Void> handlerException(Exception ex) {
        log.error("throw exception, ", ex);
        return ResultUtil.buildResultError(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BaseResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String msg;
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (!ObjectUtils.isEmpty(fieldError)) {
            msg = fieldError.getDefaultMessage();
        } else {
            msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }

        return ResultUtil.buildResultParamError(msg);
    }

}
