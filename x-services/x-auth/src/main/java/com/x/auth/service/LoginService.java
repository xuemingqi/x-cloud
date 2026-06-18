package com.x.auth.service;

import com.x.auth.domain.VerificationCodeDo;
import com.x.auth.domain.VerificationImgDo;
import com.x.auth.dto.VerificationImgDto;
import com.x.common.dto.UserInfo;
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
    BaseResult<String> login(String mobile, String password);

    /**
     * 获取图片验证码
     *
     * @return 图片验证码信息
     */
    BaseResult<VerificationCodeDo> getVerificationCode();

    /**
     * 验证码校验
     *
     * @param id   验证码id
     * @param code 验证码内容
     */
    void checkVerificationCode(Long id, String code);

    /**
     * 检查登陆失败次数
     *
     * @param mobile 手机号
     */
    void checkLoginFailTimes(String mobile);

    /**
     * 退出登录接口
     *
     * @return 退出结果
     */
    BaseResult<Void> logout();

    /**
     * 权限认证接口
     *
     * @return 认证结果
     */
    BaseResult<UserInfo> auth();

    /**
     * 获取验证码图片
     *
     * @return 验证码图片
     */
    BaseResult<VerificationImgDo> getVerificationImg();

    /**
     * 验证获取滑动图片
     *
     * @param verificationImgDto 验证码信息
     * @return 验证结果
     */
    BaseResult<Void> verificationImg(VerificationImgDto verificationImgDto);
}
