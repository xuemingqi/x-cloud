package com.x.common.response;

import cn.hutool.core.util.StrUtil;
import com.x.common.enums.ResponseCodeEnum;
import org.springframework.http.HttpStatus;

/**
 * 通用返回工具类
 *
 * @author xuemingqi
 */
@SuppressWarnings("unused")
public class ResultUtil<T> {

    private static <T> BaseResult<T> result(HttpStatus status, int code, String msg, T data) {
        return BaseResult.<T>builder()
                .status(status)
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> BaseResult<T> buildResult(ResponseCodeEnum response, T data) {
        return result(response.getHttpStatus(), response.getCode(), response.getMsg(), data);
    }

    public static <T> BaseResult<T> buildResultSuccess(T data) {
        return buildResult(ResponseCodeEnum.SUCCESS, data);
    }

    public static <T> BaseResult<T> buildResultMsg(ResponseCodeEnum codeEnum, String msg) {
        return result(codeEnum.getHttpStatus(), codeEnum.getCode(), msg, null);
    }

    public static <T> BaseResult<T> buildResultParamError(String msg) {
        return buildResultMsg(ResponseCodeEnum.BAD_REQUEST, msg);
    }

    public static <T> BaseResult<BasePageResult<T>> buildResultSuccess(Integer page, Integer pageSize, Integer total, T data) {
        return buildResultSuccess(BasePageResult.getInstance(page, pageSize, total, data));
    }

    public static <T> BaseResult<T> buildResultSuccess() {
        return buildResultSuccess(null);
    }

    public static <T> BaseResult<T> buildResultError(ResponseCodeEnum codeEnum) {
        return buildResult(codeEnum, null);
    }

    public static BaseResult<Void> buildVoidResultError(String msg) {
        return buildResultMsg(ResponseCodeEnum.INTERNAL_SERVER_ERROR, msg);
    }

    public static <T> BaseResult<T> buildGeneralResultError() {
        return buildResultError(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }

    public static <T> BaseResult<T> buildParseResult(ResponseCodeEnum codeEnum, Object... str) {
        return result(codeEnum.getHttpStatus(), codeEnum.getCode(), StrUtil.format(codeEnum.getMsg(), str), null);
    }

    public static <T> BaseResult<T> buildParseDataResult(ResponseCodeEnum codeEnum, T data, Object... str) {
        return result(codeEnum.getHttpStatus(), codeEnum.getCode(), StrUtil.format(codeEnum.getMsg(), str), data);
    }

}
