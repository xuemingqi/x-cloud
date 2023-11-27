package com.x.auth.controller;

import com.x.auth.api.LoginApi;
import com.x.auth.dto.LoginDto;
import com.x.auth.service.LoginService;
import com.x.common.enums.ResponseCodeEnum;
import com.x.common.response.BaseResult;
import com.x.common.response.ResultUtil;
import com.x.config.annotation.PassToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author : xuemingqi
 * @since : 2023/1/10 14:03
 */
@Slf4j
@Tag(name = "Login", description = "登录")
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class LoginController implements LoginApi {

    private final LoginService loginService;

    @PassToken
    @Operation(tags = "Login", summary = "登录接口", method = "/login", description = "登录接口")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<?> login(@RequestBody @Valid LoginDto loginDto) {
        boolean check = loginService.checkVerificationCode(loginDto.getCodeId(), loginDto.getCodeValue());
        if (!check) {
            return ResultUtil.buildResultError(ResponseCodeEnum.VERIFICATION_ERROR);
        }
        boolean checkFailTimes = loginService.checkLoginFailTimes(loginDto.getMobile());
        if (!checkFailTimes) {
            return ResultUtil.buildResultError(ResponseCodeEnum.MOBILE_LOCK_ERROR);
        }
        return loginService.login(loginDto.getMobile(), loginDto.getPassword());
    }


    @Operation(tags = "Login", summary = "登录接口", method = "/login", description = "登录接口")
    @PostMapping("/logout")
    public BaseResult<?> logout() {
        return loginService.logout();
    }

    @PassToken
    @Operation(tags = "Login", summary = "获取验证码", method = "/verification/code", description = "获取验证码")
    @GetMapping("/verification/code")
    public BaseResult<?> getVerificationCode() {
        return loginService.getVerificationCode();
    }


    @PassToken
    @Override
    @Operation(tags = "Login", summary = "验证权限", method = "/check", description = "验证权限")
    @PostMapping("/check")
    public BaseResult<?> check() {
        return loginService.auth();
    }

}
