package com.x.work.controller;

import com.x.framework.common.response.BaseResult;
import com.x.framework.common.response.ResultUtil;
import com.x.framework.web.annotation.PassToken;
import com.x.work.service.TransactionalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : xuemingqi
 * @since : 2024-10-17 13:10
 */
@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TestController {

    private final TransactionalTestService transactionalTestService;


    @PassToken
    @GetMapping(value = "/transactional", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResult<Void> transactional() {
        transactionalTestService.transactional();
        return ResultUtil.buildResultSuccess();
    }
}
