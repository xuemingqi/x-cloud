package com.x.work.controller;

import com.x.common.enums.OperationEnum;
import com.x.common.response.BaseResult;
import com.x.config.annotation.AccessLimit;
import com.x.config.annotation.PassToken;
import com.x.work.domain.MenuDo;
import com.x.work.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:43
 */
@Slf4j
@Tag(name = "Menu", description = "菜单相关接口")
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MenuController {

    private final MenuService menuService;

    @Operation(tags = "Menu", summary = "获取菜单列表接口", description = "获取菜单列表接口")
    @PassToken
    @AccessLimit(counts = 10, types = OperationEnum.READ)
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<List<MenuDo>> getMenuList() {
        return menuService.getMenuList();
    }
}
