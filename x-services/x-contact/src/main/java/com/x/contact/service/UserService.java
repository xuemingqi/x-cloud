package com.x.contact.service;


import com.x.common.response.BaseResult;
import com.x.contact.domain.UserDo;
import com.x.contact.dto.CreateUserDto;
import com.x.contact.dto.UserAuthDto;

/**
 * @author : xuemingqi
 * @since : 2023/1/8 16:50
 */
public interface UserService {

    /**
     * 根据用户id获取用户信息
     *
     * @param mobile 用户手机号
     */
    BaseResult<UserDo> getOneUser(String mobile);

    /**
     * 验证用户合法性
     *
     * @param param 用户信息
     */
    BaseResult<Long> userAuth(UserAuthDto param);

    /**
     * 创建用户
     *
     * @param param 用户信息
     */
    BaseResult<Void> createUser(CreateUserDto param);
}
