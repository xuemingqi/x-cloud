package com.x.auth.controller;

import com.x.api.auth.api.LoginApi;
import com.x.api.auth.domain.VerificationCodeDo;
import com.x.api.auth.domain.VerificationImgDo;
import com.x.api.auth.dto.LoginDto;
import com.x.api.auth.dto.VerificationImgDto;
import com.x.auth.service.LoginService;
import com.x.framework.common.constants.CommonConstant;
import com.x.framework.common.dto.UserInfo;
import com.x.framework.common.response.BaseResult;
import com.x.framework.web.annotation.PassToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
    public BaseResult<String> login(@RequestBody @Valid LoginDto loginDto) {
        loginService.checkVerificationCode(loginDto.getCodeId(), loginDto.getCodeValue());
        loginService.checkLoginFailTimes(loginDto.getMobile());
        return loginService.login(loginDto.getMobile(), loginDto.getPassword());
    }

    @Operation(tags = "Login", summary = "登出接口", method = "/logout", description = "登出接口")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @PostMapping("/logout")
    public BaseResult<Void> logout() {
        return loginService.logout();
    }

    @PassToken
    @Operation(tags = "Login", summary = "获取验证码", method = "/verification/code", description = "获取验证码")
    @GetMapping("/verification/code")
    public BaseResult<VerificationCodeDo> getVerificationCode() {
        return loginService.getVerificationCode();
    }

    @PassToken
    @Operation(tags = "Login", summary = "获取滑动图片验证码", method = "/verification/img", description = "获取滑动图片验证码")
    @GetMapping("/verification/img")
    public BaseResult<VerificationImgDo> getVerificationImg() {
        return loginService.getVerificationImg();
    }

    @PassToken
    @Operation(tags = "Login", summary = "验证获取滑动图片", method = "/verification/img", description = "验证获取滑动图片")
    @PostMapping("/verification/img")
    public BaseResult<Void> verificationImg(@RequestBody @Valid VerificationImgDto verificationImgDto) {
        return loginService.verificationImg(verificationImgDto);
    }

    @PassToken
    @Override
    @Operation(tags = "Login", summary = "验证权限", method = "/check", description = "验证权限")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @PostMapping("/check")
    public BaseResult<UserInfo> check() {
        return loginService.auth();
    }

}
