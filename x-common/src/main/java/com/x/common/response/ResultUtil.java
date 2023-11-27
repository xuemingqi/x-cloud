package com.x.common.response;

import com.x.common.enums.ResponseCodeEnum;

/**
 * 通用返回工具类
 *
 * @author xuemingqi
 */
@SuppressWarnings("unused")
public class ResultUtil<T> {

    private final BaseResult<T> result = BaseResult.<T>builder().build();

    public ResultUtil<T> code(int code) {
        this.result.setCode(code);
        return this;
    }

    public ResultUtil<T> msg(String msg) {
        this.result.setMsg(msg);
        return this;
    }

    public ResultUtil<T> data(T data) {
        this.result.setData(data);
        return this;
    }

    public BaseResult<T> result() {
        return this.result;
    }

    private static <T> BaseResult<T> result(int code, String msg, T data) {
        return BaseResult.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> BaseResult<T> buildResult(ResponseCodeEnum response, T data) {
        return result(response.getCode(), response.getMsg(), data);
    }

    public static <T> BaseResult<T> buildResultSuccess(T data) {
        return buildResult(ResponseCodeEnum.SUCCESS, data);
    }

    public static <T> BaseResult<T> buildResultMsg(ResponseCodeEnum codeEnum, String msg) {
        return result(codeEnum.getCode(), msg, null);
    }

    public static <T> BaseResult<T> buildResultParamError(String msg) {
        return buildResultMsg(ResponseCodeEnum.BAD_REQUEST, msg);
    }

    public static <T> BaseResult<BasePageResult<T>> buildResultSuccess(Integer page, Integer pageSize, Integer total, T data) {
        BasePageResult<T> pageResult = BasePageResult.getInstance(page, pageSize, total, data);
        return buildResultSuccess(pageResult);
    }

    public static <T> BaseResult<T> buildResultSuccess() {
        return buildResultSuccess(null);
    }

    public static <T> BaseResult<T> buildResultError(ResponseCodeEnum codeEnum) {
        return buildResult(codeEnum, null);
    }

    public static BaseResult<Void> buildGeneralResultError() {
        return buildResultError(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }

    public static <T> BaseResult<T> buildVoidResultError() {
        return buildResultError(ResponseCodeEnum.INTERNAL_SERVER_ERROR);
    }

    public static <T> BaseResult<T> buildParseResult(ResponseCodeEnum codeEnum, Object... str) {
        return result(codeEnum.getCode(), String.format(codeEnum.getMsg(), str), null);
    }

}
