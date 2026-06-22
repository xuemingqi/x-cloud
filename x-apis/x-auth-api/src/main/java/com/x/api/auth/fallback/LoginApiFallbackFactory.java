package com.x.api.auth.fallback;

import com.x.api.auth.api.LoginApi;
import com.x.framework.common.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2023/11/22 13:25
 */
@Slf4j
@Component
public class LoginApiFallbackFactory implements FallbackFactory<LoginApi> {
    @Override
    public LoginApi create(Throwable cause) {
        return () -> {
            log.error("server error,fallback!");
            return ResultUtil.buildGeneralResultError();
        };
    }
}
