package com.x.chat.controller;

import com.x.chat.param.request.SendMessagesReq;
import com.x.chat.param.response.UserMessagesRes;
import com.x.chat.service.MessagesService;
import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2025/04/03 15:55
 */
@Tag(name = "Messages", description = "聊天消息相关接口")
@Validated
@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MessagesController {

    private final MessagesService messagesService;


    @Operation(tags = "Messages", summary = "获取群组历史消息", method = "/{id}", description = "获取群组历史消息接口")
    @Parameters({
            @Parameter(name = "id", description = "群组id", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/group/{id}")
    public BaseResult<List<UserMessagesRes>> getMessagesByGroupId(@PathVariable("id") Long id) {
        return ResultUtil.buildResultSuccess(messagesService.getMessagesByGroupId(id));
    }


    @Operation(tags = "Messages", summary = "获取用户历史消息", method = "/user/{id}", description = "获取用户历史消息接口")
    @Parameters({
            @Parameter(name = "id", description = "用户id", in = ParameterIn.PATH, required = true)
    })
    @GetMapping("/user/{id}")
    public BaseResult<List<UserMessagesRes>> getUserMessages(@PathVariable("id") Long id) {
        return ResultUtil.buildResultSuccess(messagesService.getMessagesByUserId(id));
    }


    @Operation(tags = "Messages", summary = "发送消息", method = "/send", description = "发送消息接口")
    @PostMapping("/send")
    public BaseResult<Void> sendMessages(@RequestBody @Valid SendMessagesReq sendMessagesReq) {
        messagesService.sendMessages(sendMessagesReq);
        return ResultUtil.buildResultSuccess();
    }
}
