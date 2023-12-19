package com.x.work.controller;

import com.x.common.response.BaseResult;
import com.x.config.annotation.PassToken;
import com.x.work.service.WeChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 16:04
 */
@Tag(name = "WX", description = "md相关接口")
@PassToken
@RestController
@RequestMapping("/wx")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WeChatController {

    private final WeChatService weChatService;


    @Operation(tags = "WX", summary = "获取验证图片", method = "/userInfo", description = "获取验证图片")
    @GetMapping("/qr")
    public void getQr(@RequestParam("state") String state) {
        weChatService.getQr(state);
    }

    @Operation(tags = "WX", summary = "获取验证链接", method = "/userInfo", description = "获取验证链接")
    @GetMapping("/qr/data")
    public BaseResult<?> getQrData(@RequestParam("state") String state) {
        return weChatService.getQrData(state);
    }

    @Operation(tags = "WX", summary = "获取用户详情接口", method = "/userInfo", description = "获取用户详情接口")
    @GetMapping("/user/info")
    public BaseResult<?> userInfo(@RequestParam("code") String code, @RequestParam("state") String state) {
        return weChatService.userInfo(code, state);
    }
}
