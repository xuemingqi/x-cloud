package com.x.contact.controller;

import com.x.common.response.BaseResult;
import com.x.config.annotation.Internal;
import com.x.contact.api.UserApi;
import com.x.contact.domain.UserDo;
import com.x.contact.dto.CreateUserDto;
import com.x.contact.dto.UserAuthDto;
import com.x.contact.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping("/{mobile}/info")
    public BaseResult<UserDo> getUserInfo(@PathVariable("mobile") String mobile) {
        return userService.getOneUser(mobile);
    }

    @Override
    @Internal
    @Operation(tags = "User", summary = "验证用户", method = "/auth", description = "验证用户")
    @PostMapping("/auth")
    public BaseResult<Long> userAuth(@RequestBody @Valid UserAuthDto param) {
        return userService.userAuth(param);
    }


    @Operation(tags = "User", summary = "添加用户", method = "/create", description = "添加用户")
    @PostMapping("/create")
    public BaseResult<Void> createUser(@RequestBody @Valid CreateUserDto param) {
        return userService.createUser(param);
    }
}
