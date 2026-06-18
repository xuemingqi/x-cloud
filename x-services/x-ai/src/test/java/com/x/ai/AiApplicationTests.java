package com.x.ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.config.import=",
        "spring.cloud.nacos.config.enabled=false",
        "spring.cloud.nacos.discovery.enabled=false",
        "agentscope.provider=ollama",
        "agentscope.model=ollama:llama3"
})
class AiApplicationTests {

    @Test
    void contextLoads() {
    }

}
