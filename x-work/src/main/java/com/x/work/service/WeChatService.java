package com.x.work.service;

import com.x.common.response.BaseResult;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 15:58
 */
public interface WeChatService {

    /**
     * 获取扫码登录二维码
     *
     * @param state state
     */
    void getQr(String state);

    /**
     * 获取扫码登录二维码
     *
     * @param state state
     * @return 二维码
     */
    BaseResult<?> getQrData(String state);

    /**
     * 获取用户信息
     *
     * @param code  code
     * @param state state
     * @return 用户信息
     */
    BaseResult<?> userInfo(String code, String state);
}
