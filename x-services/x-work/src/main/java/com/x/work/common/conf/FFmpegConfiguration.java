package com.x.work.common.conf;

import com.x.work.common.property.FFmpegProperty;
import jakarta.annotation.Resource;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author : xuemingqi
 * @since : 2025/12/24 14:56
 */
@Configuration
public class FFmpegConfiguration {

    @Resource
    private FFmpegProperty fFmpegProperty;


    @Bean
    public FFmpeg ffmpeg() throws IOException {
        return new FFmpeg(fFmpegProperty.getFfmpegPath());
    }

    @Bean
    public FFprobe ffprobe() throws IOException {
        return new FFprobe(fFmpegProperty.getFfprobePath());
    }
}
