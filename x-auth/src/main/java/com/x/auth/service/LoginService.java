package com.x.auth.service;

import com.x.common.response.BaseResult;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 14:03
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param mobile   手机号
     * @param password 密码
     * @return token
     */
    BaseResult<?> login(String mobile, String password);

    /**
     * 获取图片验证码
     *
     * @return 图片验证码信息
     */
    BaseResult<?> getVerificationCode();

    /**
     * 验证码校验
     *
     * @param id   验证码id
     * @param code 验证码内容
     * @return 校验结果
     */
    Boolean checkVerificationCode(Long id, String code);

    /**
     * 检查登陆失败次数
     *
     * @param mobile 手机号
     * @return 检查结果
     */
    Boolean checkLoginFailTimes(String mobile);

    /**
     * 退出登录接口
     *
     * @return 退出结果
     */
    BaseResult<?> logout();

    /**
     * 权限认证接口
     *
     * @return 认证结果
     */
    BaseResult<?> auth();

}
