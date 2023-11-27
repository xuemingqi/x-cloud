package com.x.work.test;

import cn.hutool.core.lang.Snowflake;
import com.x.config.mq.util.MqUtil;
import com.x.websocket.api.WebSocketApi;
import com.x.websocket.dto.MessageDto;
import com.x.work.WorkApplication;
import com.x.work.mq.constants.TopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author : xuemingqi
 * @since : 2023/2/4 10:46
 */
@Slf4j
@SpringBootTest(classes = WorkApplication.class)
public class ProducerTest {

    @Resource
    private MqUtil mqUtil;

    @Resource
    private Snowflake snowflake;

    @Resource
    WebSocketApi webSocketApi;

    @Test
    public void test() {
        mqUtil.convertAndSend(TopicConstant.TOPIC_A, "123456");
    }

    @Test
    public void IdTest() {
        assert false :
                "出问题了";
        System.out.println(snowflake.nextId());
    }

    @Test
    public void test2() {
        MessageDto<String> messageDto = MessageDto.<String>builder().state(1).data("test message").build();
        webSocketApi.pub(123456L, messageDto);
    }

    @BeforeAll
    public static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("@AfterAll");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}


