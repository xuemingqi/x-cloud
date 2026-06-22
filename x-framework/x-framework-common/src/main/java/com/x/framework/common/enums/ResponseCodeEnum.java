package com.x.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 通用的返回码
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    /**
     * 基础
     */
    SUCCESS(HttpStatus.OK, 200, "success!"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "参数错误！"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, 404, "不存在"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "请求方式错误！"),
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS, 429, "too many requests"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "发生未知错误，请联系管理员！"),

    /**
     * auth模块
     */
    PERMISSION_ERROR(HttpStatus.BAD_REQUEST, 10400, "token过期！"),
    VERIFICATION_ERROR(HttpStatus.BAD_REQUEST, 10401, "验证码错误！"),
    CONTACT_TOKEN_ERROR(HttpStatus.BAD_REQUEST, 10402, "账号或密码错误，您还可以尝试{}次。连续累计输错五次，账号将锁定30分钟。或通过忘记密码进行重置。如您未注册，请先申请账号。"),
    MOBILE_LOCK_ERROR(HttpStatus.BAD_REQUEST, 10403, "账号锁定30分钟，请稍后重试。"),
    INTERNAL_ERROR(HttpStatus.BAD_REQUEST, 10400, "内部token过期！"),
    IMG_POSITION_ERROR(HttpStatus.BAD_REQUEST, 10404, "图片验证码位置错误！"),
    IMG_NOT_FOUND(HttpStatus.BAD_REQUEST, 10405, "图片验证码不存在！"),
    IMG_EXPIRED(HttpStatus.BAD_REQUEST, 10406, "验证码已过期！"),

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
    OPENAI_ERROR(HttpStatus.BAD_REQUEST, 12002, "chatGPT服务错误,请重试！"),
    WX_GET_USER_INFO_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 12003, "获取微信用户信息错误"),

    /**
     * gateway模块
     */
    FLOW_BLOCK(HttpStatus.TOO_MANY_REQUESTS, 13001, "flow block"),
    DEGRADE_BLOCK(HttpStatus.BAD_REQUEST, 13002, "degrade block"),
    PARAM_FLOW_BLOCK(HttpStatus.BAD_REQUEST, 13003, "param flow block"),
    SYSTEM_BLOCK(HttpStatus.BAD_REQUEST, 13004, "system block"),
    AUTHORITY_BLOCK(HttpStatus.BAD_REQUEST, 13005, "authority block"),
    SENTINEL_BLOCK(HttpStatus.BAD_REQUEST, 13006, "sentinel block"),

    /**
     * chat模块
     */
    CHAT_USER_NOT_EXIST(HttpStatus.BAD_REQUEST, 14001, "用户不存在"),
    CHAT_GROUP_NOT_EXIST(HttpStatus.BAD_REQUEST, 14002, "群组不存在"),
    CHAT_GROUP_MEMBER_NOT_EXIST(HttpStatus.BAD_REQUEST, 14003, "群组成员不存在");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String msg;
}
