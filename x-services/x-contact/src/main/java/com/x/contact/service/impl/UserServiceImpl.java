package com.x.contact.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.x.framework.common.enums.ResponseCodeEnum;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import com.x.framework.common.utils.EncryptionUtil;
import com.x.framework.common.exception.XException;
import com.x.contact.db.entity.User;
import com.x.contact.db.service.UserIService;
import com.x.api.contact.domain.UserDo;
import com.x.api.contact.dto.CreateUserDto;
import com.x.api.contact.dto.UserAuthDto;
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
            throw new XException(ResponseCodeEnum.USER_NOT_EXIST);
        }
        UserDo userDo = UserDo.builder().build();
        BeanUtils.copyProperties(user, userDo);
        if (user.getSex() != null) {
            userDo.setSex(com.x.api.contact.enums.Sex.valueOf(user.getSex().name()));
        }
        return ResultUtil.buildResultSuccess(userDo);
    }

    @Override
    public BaseResult<Long> userAuth(UserAuthDto param) {
        //用户是否存在
        User user = userIService.lambdaQuery()
                .eq(User::getMobile, param.getMobile())
                .one();
        if (null == user) {
            throw new XException(ResponseCodeEnum.USER_NOT_EXIST);
        }

        //密码是否正确
        String pwd = EncryptionUtil.hmacSha256(param.getPassword(), user.getSalt());
        if (!pwd.equals(user.getPassword())) {
            throw new XException(ResponseCodeEnum.USER_PWD_ERROR);
        }
        return ResultUtil.buildResultSuccess(user.getId());
    }

    @Override
    public BaseResult<Void> createUser(CreateUserDto param) {
        String mobile = param.getMobile();
        if (userExist(mobile)) {
            throw new XException(ResponseCodeEnum.MOBILE_EXIST);
        }

        User user = User.builder().build();
        BeanUtils.copyProperties(param, user);
        if (param.getSex() != null) {
            user.setSex(com.x.contact.db.enums.Sex.valueOf(param.getSex().name()));
        }

        //密码sha256加密
        String salt = IdUtil.simpleUUID();
        String encryptPwd = EncryptionUtil.hmacSha256(param.getPassword(), salt);
        user.setId(snowflake.nextId()).setSalt(salt).setPassword(encryptPwd);

        boolean result = userIService.save(user);
        if (result) {
            return ResultUtil.buildResultSuccess();
        }
        throw new XException(ResponseCodeEnum.CREATE_USER_ERROR);
    }

    private boolean userExist(String mobile) {
        return userIService.lambdaQuery()
                .eq(User::getMobile, mobile)
                .exists();
    }
}
