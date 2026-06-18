package com.x.work.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author : xuemingqi
 * @since : 2025/12/24 14:57
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "ffmpeg")
public class FFmpegProperty {

    private String ffmpegPath;

    private String ffprobePath;
}
