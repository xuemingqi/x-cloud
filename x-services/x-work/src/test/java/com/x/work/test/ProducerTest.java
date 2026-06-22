package com.x.work.test;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.x.framework.common.message.TestMessage;
import com.x.framework.mq.service.MqService;
import com.x.api.websocket.api.WebSocketApi;
import com.x.api.websocket.dto.MessageDto;
import com.x.work.WorkApplication;
import com.x.work.schedule.job.StrJob;
import com.x.work.schedule.service.CommonScheduleService;
import com.x.work.schedule.service.HelloScheduleService;
import com.x.work.service.SseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author : xuemingqi
 * @since : 2023/2/4 10:46
 */
@Slf4j
@SpringBootTest(classes = WorkApplication.class)
public class ProducerTest {

    @Resource
    private MqService mqService;

    @Resource
    private Snowflake snowflake;

    @Resource
    private WebSocketApi webSocketApi;

    @Resource
    private HelloScheduleService helloScheduleService;

    @Resource
    private CommonScheduleService commonScheduleService;

    @Resource
    private SseService sseService;


    @Test
    public void addScheduleTest() {
        helloScheduleService.addHelloJob(123456L, LocalDateTime.now().plusMinutes(2));
        commonScheduleService.addSchedule(StrJob.class, "xuemingqi", "0/10 * * * * ?");
    }

    @Test
    public void deleteScheduleTest() {
        helloScheduleService.deleteHelloJob(123456L);
    }

    @Test
    public void test() {
        mqService.convertAndSend(new TestMessage().setMsg("test message4"));
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
    public void sseTest() {
        sseService.notify("123456", "test message");
    }

    @Test
    public void pushTest() {
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
                    System.out.println("Git push successful");
                    break;
                } else {
                    System.out.println("Git push failed. Retrying...");
                    //读取错误信息
                    printStream(process.getErrorStream());
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @NacosInjected
    private NamingService namingService;

    public static void main(String[] args) throws NacosException, InterruptedException {
        String serverAddr = "localhost";
        String dataId = "x-work.yaml";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, "2ef6f7e9-aeaf-4276-9ba3-aefe4372666c");
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);

        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);
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

    //打印输入流的内容
    private void printStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}


