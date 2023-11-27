package com.x.contact.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.common.utils.EncryptionUtil;
import com.x.common.utils.UUIDUtil;
import com.x.contact.db.entity.User;
import com.x.contact.db.service.UserIService;
import com.x.contact.domain.UserDo;
import com.x.contact.dto.CreateUserDto;
import com.x.contact.dto.UserAuthDto;
import com.x.contact.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/1/8 16:51
 */
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    private final UserIService userIService;

    private final Snowflake snowflake;

    @Override
    public BaseResult<UserDo> getOneUser(String mobile) {
        User user = userIService.lambdaQuery()
                .eq(User::getMobile, mobile)
                .one();
        if (null == user) {
            return ResultUtil.buildResultError(ResponseCodeEnum.USER_NOT_EXIST);
        }
        UserDo userDo = UserDo.builder().build();
        BeanUtils.copyProperties(user, userDo);
        return ResultUtil.buildResultSuccess(userDo);
    }

    @Override
    public BaseResult<Long> userAuth(UserAuthDto param) {
        //用户是否存在
        User user = userIService.lambdaQuery()
                .eq(User::getMobile, param.getMobile())
                .one();
        if (null == user) {
            return ResultUtil.buildResultError(ResponseCodeEnum.USER_NOT_EXIST);
        }

        //密码是否正确
        String pwd = EncryptionUtil.hmacSha256(param.getPassword(), user.getSalt());
        if (!pwd.equals(user.getPassword())) {
            ResultUtil.buildResultError(ResponseCodeEnum.USER_PWD_ERROR);
        }
        return ResultUtil.buildResultSuccess(user.getId());
    }

    @Override
    public BaseResult<Void> createUser(CreateUserDto param) {
        String mobile = param.getMobile();
        if (userExist(mobile)) {
            return ResultUtil.buildResultError(ResponseCodeEnum.MOBILE_EXIST);
        }

        User user = User.builder().build();
        BeanUtils.copyProperties(param, user);

        //密码sha256加密
        String salt = UUIDUtil.generatorId();
        String encryptPwd = EncryptionUtil.hmacSha256(param.getPassword(), salt);
        user.setId(snowflake.nextId()).setSalt(salt).setPassword(encryptPwd);

        boolean result = userIService.save(user);
        if (result) {
            return ResultUtil.buildResultSuccess();
        }
        return ResultUtil.buildResultError(ResponseCodeEnum.CREATE_USER_ERROR);
    }

    private boolean userExist(String mobile) {
        return userIService.lambdaQuery()
                .eq(User::getMobile, mobile)
                .exists();
    }
}
