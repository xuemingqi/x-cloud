package com.x.auth.api;

import com.x.auth.fallback.LoginApiFallbackFactory;
import com.x.common.response.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 16:17
 */
@FeignClient(name = "x-auth", fallbackFactory = LoginApiFallbackFactory.class)
public interface LoginApi {

    @ResponseBody
    @PostMapping("/auth/check")
    BaseResult<?> check();
}
