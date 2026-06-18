package com.x.work.mq.service.impl;

import com.x.common.message.TestMessage;
import com.x.common.utils.JsonUtil;
import com.x.work.mq.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2024-10-16 09:50
 */
@Slf4j
@Service
public class TestServiceImpl implements TestService {


    @Override
    public void test(TestMessage message) {
        log.info("consumer:{}", JsonUtil.toJsonStr(message));
    }
}
