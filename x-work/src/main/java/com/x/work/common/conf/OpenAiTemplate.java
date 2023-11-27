package com.x.work.common.conf;

import com.theokanning.openai.OpenAiService;

import java.time.Duration;

/**
 * @author : xuemingqi
 * @since : 2023/2/27 15:58
 */
public class OpenAiTemplate extends OpenAiService {
    public OpenAiTemplate(String token, Duration timeout) {
        super(token, timeout);
    }
}
