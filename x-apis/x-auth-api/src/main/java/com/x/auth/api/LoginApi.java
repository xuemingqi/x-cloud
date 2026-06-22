package com.x.auth.api;

import com.x.framework.common.config.InternalInterceptor;
import com.x.framework.common.dto.UserInfo;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 16:17
 */
@FeignClient(name = "x-auth", configuration = InternalInterceptor.class, contextId = "x-auth-login-api")
@Retryable(retryFor = {Exception.class}, backoff = @Backoff(delay = 1000L, multiplier = 1))
public interface LoginApi {

    @ResponseBody
    @PostMapping("/auth/check")
    BaseResult<UserInfo> check();

    @Recover
    default BaseResult<?> recover(Exception exception) {
        return ResultUtil.buildVoidResultError(exception.getMessage());
    }
}
