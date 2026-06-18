package com.x.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author : xuemingqi
 * @since : 2024-11-07 10:56
 */
public class IdUtil extends cn.hutool.core.util.IdUtil {

    /**
     * 生成随机字母数字组合
     *
     * @param length 长度
     * @return 随机字母数字组合
     */
    public static String randomAlphanumeric(int length) {
        return RandomStringUtils.insecure().nextAlphanumeric(length).toLowerCase();
    }
}
