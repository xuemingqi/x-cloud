package com.x.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 通用的返回码
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    //基础
    SUCCESS(HttpStatus.OK, 200, "success!"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "参数错误！"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "发生未知错误，请联系管理员！"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, 404, "不存在"),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, 429, "too many requests"),


    /**
     * auth模块
     */
    PERMISSION_ERROR(HttpStatus.BAD_REQUEST, 10400, "token过期！"),
    //登录相关
    VERIFICATION_ERROR(HttpStatus.BAD_REQUEST, 10401, "验证码错误！"),
    CONTACT_TOKEN_ERROR(HttpStatus.BAD_REQUEST, 10402, "账号或密码错误，您还可以尝试%s次。连续累计输错五次，账号将锁定30分钟。或通过忘记密码进行重置。如您未注册，请先申请账号。"),
    MOBILE_LOCK_ERROR(HttpStatus.BAD_REQUEST, 10403, "账号锁定30分钟，请稍后重试。"),
    INTERNAL_ERROR(HttpStatus.BAD_REQUEST, 10400, "内部token过期！"),


    /**
     * contact模块
     */
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, 11001, "用户不存在"),
    MOBILE_EXIST(HttpStatus.BAD_REQUEST, 11002, "手机号已经存在"),
    CREATE_USER_ERROR(HttpStatus.BAD_REQUEST, 11003, "添加用户失败"),
    USER_PWD_ERROR(HttpStatus.BAD_REQUEST, 11004, "密码错误"),


    /**
     * work模块
     */
    FILE_NOT_EXIST(HttpStatus.BAD_REQUEST, 12001, "文件不存在"),
    OPENAI_ERROR(HttpStatus.BAD_REQUEST, 12002, "chatGPT服务错误,请重试！");


    private final HttpStatus httpStatus;
    private final Integer code;
    private final String msg;
}
