package com.x.common.utils;

import java.util.UUID;

/**
 * @author : xuemingqi
 * @since : 2023/1/7 18:53
 */
public class UUIDUtil {

    /**
     * 生成32位Id号
     */
    public static String generatorId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
