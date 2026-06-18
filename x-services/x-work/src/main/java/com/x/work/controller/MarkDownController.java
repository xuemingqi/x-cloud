package com.x.work.controller;

import com.x.common.enums.OperationEnum;
import com.x.common.response.BaseResult;
import com.x.framework.redis.annotation.AccessLimit;
import com.x.framework.web.annotation.PassToken;
import com.x.work.domain.FileSimpleDo;
import com.x.work.service.MarkDownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/23 17:23
 */
@Slf4j
@Tag(name = "MarkDown", description = "md相关接口")
@RestController
@RequestMapping("/md")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MarkDownController {

    private final MarkDownService markDownService;


    @Operation(tags = "MarkDown", summary = "获取md文件内容接口", method = "/{id}", description = "获取md文件内容接口")
    @Parameters({
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH, required = true)
    })
    @PassToken
    @AccessLimit(counts = 10, types = OperationEnum.READ)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<String> getMdFile(@PathVariable("id") Long id) {
        return markDownService.getMdFileStr(id);
    }


    @Operation(tags = "MarkDown", summary = "获取md文件索引列表", method = "/list/{menuId}", description = "获取md文件索引列表")
    @Parameters({
            @Parameter(name = "menuId", description = "目录id", in = ParameterIn.PATH, required = true)
    })
    @PassToken
    @AccessLimit(counts = 10, types = OperationEnum.READ)
    @GetMapping(value = "/list/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<List<FileSimpleDo>> getMdList(@PathVariable("menuId") Long menuId) {
        return markDownService.getMdList(menuId);
    }
}
