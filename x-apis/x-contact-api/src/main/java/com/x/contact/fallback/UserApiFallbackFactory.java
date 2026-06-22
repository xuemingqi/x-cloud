package com.x.contact.fallback;

import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import com.x.contact.api.UserApi;
import com.x.contact.domain.UserDo;
import com.x.contact.dto.CreateUserDto;
import com.x.contact.dto.UserAuthDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/11/23 18:06
 */
@Slf4j
@Component
public class UserApiFallbackFactory implements FallbackFactory<UserApi> {
    @Override
    public UserApi create(Throwable cause) {
        return new UserApi() {
            @Override
            public BaseResult<UserDo> getUserInfo(String mobile) {
                log.info("get user server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Long> userAuth(UserAuthDto param) {
                log.info("user auth server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }

            @Override
            public BaseResult<Void> createUser(CreateUserDto param) {
                log.info("create user server error,fallback!");
                return ResultUtil.buildGeneralResultError();
            }
        };
    }
}
