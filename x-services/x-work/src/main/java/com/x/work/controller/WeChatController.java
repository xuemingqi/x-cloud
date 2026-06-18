package com.x.work.controller;

import com.x.common.response.BaseResult;
import com.x.framework.web.annotation.PassToken;
import com.x.work.service.WeChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xuemingqi
 * @since : 2023/12/5 16:04
 */
@Tag(name = "WX", description = "微信相关接口")
@PassToken
@RestController
@RequestMapping("/wx")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WeChatController {

    private final WeChatService weChatService;


    @Operation(tags = "WX", summary = "获取验证图片", method = "/userInfo", description = "获取验证图片")
    @Parameters({
            @Parameter(name = "state", description = "用户随机编码", in = ParameterIn.QUERY, required = true)
    })
    @GetMapping("/qr")
    public void getQr(@RequestParam("state") String state) {
        weChatService.getQr(state);
    }

    @Operation(tags = "WX", summary = "获取验证链接", method = "/userInfo", description = "获取验证链接")
    @Parameters({
            @Parameter(name = "state", description = "用户随机编码", in = ParameterIn.QUERY, required = true)
    })
    @GetMapping("/qr/data")
    public BaseResult<String> getQrData(@RequestParam("state") String state) {
        return weChatService.getQrData(state);
    }

    @Operation(tags = "WX", summary = "获取用户详情接口", method = "/userInfo", description = "获取用户详情接口")
    @Parameters({
            @Parameter(name = "state", description = "用户随机编码", in = ParameterIn.QUERY, required = true),
            @Parameter(name = "code", description = "用户code", in = ParameterIn.QUERY, required = true)
    })
    @GetMapping("/user/info")
    public BaseResult<WxMpUser> userInfo(@RequestParam("code") String code, @RequestParam("state") String state) {
        return weChatService.userInfo(code, state);
    }
}
