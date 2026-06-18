package com.x.work.controller;

import com.x.common.constants.CommonConstant;
import com.x.common.response.BaseResult;
import com.x.work.domain.NginxLogDo;
import com.x.work.dto.ServerMessageDto;
import com.x.work.service.NginxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

/**
 * @author : xuemingqi
 * @since : 2023/2/10 15:29
 */
@Slf4j
@Tag(name = "Nginx", description = "查询nginx日志接口")
@RestController
@RequestMapping("/nginx")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class NginxController {

    private final NginxService nginxService;


    @Operation(tags = "Nginx", summary = "获取访问地区日志", method = "/log/area", description = "获取访问地区日志接口")
    @Parameters({
            @Parameter(name = CommonConstant.TOKEN, description = "token", in = ParameterIn.HEADER, required = true)
    })
    @PostMapping(value = "/log/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<List<NginxLogDo>> getAreaLog(@RequestBody @Valid ServerMessageDto param) {
        return nginxService.getNginxLog(param);
    }
}
