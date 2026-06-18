package com.x.common.utils;

import com.x.common.dto.UserInfo;

import java.util.Optional;

/**
 * @author : xuemingqi
 * @since : 2025/04/25 11:08
 */
public class UserThreadLocalUtil {

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置用户上下文
     *
     * @param userInfo 用户上下文对象
     */
    public static void set(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }

    /**
     * 获取用户上下文
     *
     * @return 当前用户上下文，可能为null
     */
    public static UserInfo get() {
        return THREAD_LOCAL.get();
    }

    /**
     * 安全获取用户上下文（Optional方式）
     *
     * @return Optional包装的用户上下文
     */
    public static Optional<UserInfo> getOptional() {
        return Optional.ofNullable(THREAD_LOCAL.get());
    }

    /**
     * 清除用户上下文
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
