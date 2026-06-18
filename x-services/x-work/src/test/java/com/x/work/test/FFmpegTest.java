package com.x.work.test;

import com.x.work.WorkApplication;
import com.x.work.ffmpeg.FFmpegUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : xuemingqi
 * @since : 2025/12/24 15:19
 */
@Slf4j
@SpringBootTest(classes = WorkApplication.class)
public class FFmpegTest {

    @Resource
    private FFmpegUtils ffmpegUtils;


    @Test
    public void getVideoLength() {
        System.out.println(ffmpegUtils.getDuration("/Users/xuemingqi/Downloads/0_Santa_Claus_Christmas_3840x2160.mp4"));
    }

    @Test
    public void captureFrame(){
        ffmpegUtils.captureFrameAsync("/Users/xuemingqi/Downloads/0_Santa_Claus_Christmas_3840x2160.mp4", "/Users/xuemingqi/Downloads/0_Santa_Claus_Christmas_3840x2160" + ".jpg", 1000)
                .thenAccept(res -> System.out.println("封面保存成功: " + res));
    }

    @Test
    public void processVideo(){
        ffmpegUtils.processVideoAsync(
                "/Users/xuemingqi/Downloads/0_Santa_Claus_Christmas_3840x2160.mp4",
                "/Users/xuemingqi/Downloads/0_Santa_Claus_Christmas_3840x2160.gif",
                output -> output.setFormat("gif")
                        .setVideoResolution(480, 320)
                        .setVideoFrameRate(15),
                p -> System.out.println("GIF 生成进度：" + p)
        );
    }
}
