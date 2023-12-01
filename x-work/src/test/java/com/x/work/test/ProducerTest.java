package com.x.work.test;

import cn.hutool.core.lang.Snowflake;
import com.x.config.mq.util.MqUtil;
import com.x.websocket.api.WebSocketApi;
import com.x.websocket.dto.MessageDto;
import com.x.work.WorkApplication;
import com.x.work.mq.constants.TopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @Test
    public void push() {
        try {
            //项目路径
            Path currentRelativePath = Paths.get("").toAbsolutePath().getParent();
            String projectRootPath = currentRelativePath.toString();
            log.info("path:{}", projectRootPath);

            while (true) {
                ProcessBuilder processBuilder = new ProcessBuilder("git", "push");
                processBuilder.directory(new java.io.File(projectRootPath));
                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                //处理命令执行结果
                if (exitCode == 0) {
                    log.info("Git push successful");
                    break;
                } else {
                    log.info("Git push failed. Retrying...");
                    //读取错误信息
                    printStream(process.getErrorStream());
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    //打印输入流的内容
    private void printStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
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


