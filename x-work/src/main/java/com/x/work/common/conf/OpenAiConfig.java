package com.x.work.common.conf;

import com.x.work.common.property.OpenAiProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author : xuemingqi
 * @since : 2023/2/27 13:51
 */
@Slf4j
@Configuration
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OpenAiConfig {

    private final OpenAiProperty openAiProperty;

    @Bean
    public OpenAiTemplate getOpenAiService() {
        return new OpenAiTemplate(openAiProperty.getToken(), Duration.ofSeconds(openAiProperty.getTimeOut()));
    }
}
