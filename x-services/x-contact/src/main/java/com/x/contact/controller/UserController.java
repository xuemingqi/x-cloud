package com.x.contact.controller;

import com.x.framework.common.constants.CommonConstant;
import com.x.framework.common.response.BaseResult;
import com.x.api.contact.api.UserApi;
import com.x.api.contact.domain.UserDo;
import com.x.api.contact.dto.CreateUserDto;
import com.x.api.contact.dto.UserAuthDto;
import com.x.contact.service.UserService;
import com.x.framework.db.annotation.XLog;
import com.x.framework.web.annotation.Internal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : xuemingqi
 * @since : 2023/1/13 16:46
 */
@Slf4j
@Tag(name = "User", description = "用户相关")
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserController implements UserApi {

    private final UserService userService;


    @Override
    @Internal
    @Operation(tags = "User", summary = "获取用户信息", method = "/{mobile}/info", description = "获取用户信息")
    @Parameters({
            @Parameter(name = CommonConstant.INTERNAL_TOKEN, description = "内部请求token", in = ParameterIn.HEADER, required = true),
            @Parameter(name = CommonConstant.INTERNAL_TIMESTAMP, description = "时间戳（秒）", in = ParameterIn.HEADER, required = true),
    })
    @GetMapping("/{mobile}/info")
    public BaseResult<UserDo> getUserInfo(@PathVariable("mobile") String mobile) {
        return userService.getOneUser(mobile);
    }

    @Override
    @Internal
    @Operation(tags = "User", summary = "验证用户", method = "/auth", description = "验证用户")
    @Parameters({
            @Parameter(name = CommonConstant.INTERNAL_TOKEN, description = "内部请求token", in = ParameterIn.HEADER, required = true),
            @Parameter(name = CommonConstant.INTERNAL_TIMESTAMP, description = "时间戳（秒）", in = ParameterIn.HEADER, required = true),
    })
    @PostMapping("/auth")
    public BaseResult<Long> userAuth(@RequestBody @Valid UserAuthDto param) {
        return userService.userAuth(param);
    }

    @Override
    @Operation(tags = "User", summary = "添加用户", method = "/create", description = "添加用户")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @XLog(operation = "添加用户:{}", parameters = {"#param.name"})
    @PostMapping("/create")
    public BaseResult<Void> createUser(@RequestBody @Valid CreateUserDto param) {
        return userService.createUser(param);
    }
}
