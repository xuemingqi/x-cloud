package com.x.contact.controller;

import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import com.x.api.contact.constants.ValidMsgConstant;
import com.x.api.contact.param.request.AddContactReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : xuemingqi
 * @since : 2025/04/07 14:26
 */
@Tag(name = "Contact", description = "群组相关")
@Validated
@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ContactController {


    @Operation(tags = "Contact", summary = "添加联系人", method = "/add", description = "添加联系人接口")
    @PostMapping("/add")
    public BaseResult<Void> addContact(@RequestBody @Valid AddContactReq req) {
        return ResultUtil.buildResultSuccess();
    }


    @Operation(tags = "Contact", summary = "删除联系人", method = "/delete", description = "删除联系人接口")
    @DeleteMapping("/{userId}")
    public BaseResult<Void> deleteContact(@PathVariable(value = "userId")
                                          @NotNull(message = ValidMsgConstant.USER_ID_NULL_ERROR) Long userId) {
        return ResultUtil.buildResultSuccess();
    }


    @Operation(tags = "Contact", summary = "获取联系人列表", method = "/list", description = "获取联系人列表接口")
    @GetMapping("/list")
    public BaseResult<Void> getContactList() {
        return ResultUtil.buildResultSuccess();
    }
}
