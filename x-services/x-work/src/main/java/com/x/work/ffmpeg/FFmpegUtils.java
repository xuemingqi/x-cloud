package com.x.work.ffmpeg;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author : xuemingqi
 * @since : 2025/12/24 15:01
 */
@Slf4j
@Component
public class FFmpegUtils {

    private final FFmpeg ffmpeg;

    private final FFprobe ffprobe;

    private final ExecutorService executorService;


    public FFmpegUtils(FFmpeg ffmpeg, FFprobe ffprobe, @Qualifier("virtualExecutor") ExecutorService executorService) {
        this.ffmpeg = ffmpeg;
        this.ffprobe = ffprobe;
        this.executorService = executorService;
    }

    /**
     * 【同步】获取视频时长（秒）
     */
    public double getDuration(String videoPath) {
        try {
            return ffprobe.probe(videoPath).getFormat().duration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 【虚拟线程异步】截取封面图
     */
    public CompletableFuture<String> captureFrameAsync(String videoPath, String imagePath, long atMillis) {
        return CompletableFuture.supplyAsync(() -> {
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(videoPath)
                    .addOutput(imagePath)
                    .setFrames(1)
                    .setStartOffset(atMillis, TimeUnit.MILLISECONDS)
                    .setFormat("image2")
                    .done();

            new FFmpegExecutor(ffmpeg, ffprobe).createJob(builder).run();
            return imagePath;
        }, executorService);
    }

    /**
     * 通用异步视频处理方法
     *
     * @param inputPath          输入路径
     * @param outputPath         输出路径
     * @param outputConfigurator 用于配置输出参数的 Lambda (设置编码、格式、分辨率等)
     * @param progressConsumer   进度回调
     */
    public CompletableFuture<Void> processVideoAsync(String inputPath, String outputPath, Consumer<FFmpegOutputBuilder> outputConfigurator, Consumer<Double> progressConsumer) {

        return CompletableFuture.runAsync(() -> {
            try {
                // 1. 获取时长用于进度计算
                FFmpegProbeResult probeResult = ffprobe.probe(inputPath);
                final double totalDurationNs = probeResult.getFormat().duration * 1_000_000_000.0;

                // 2. 初始化 Builder
                FFmpegBuilder builder = new FFmpegBuilder()
                        .setInput(inputPath)
                        .overrideOutputFiles(true);

                // 3. 创建输出配置
                FFmpegOutputBuilder outputBuilder = builder.addOutput(outputPath);

                // 4. 执行调用者传入的自定义配置 (比如设置格式、编码器等)
                if (outputConfigurator != null) {
                    outputConfigurator.accept(outputBuilder);
                }

                // 5. 完成构建
                FFmpegBuilder finalBuilder = outputBuilder.done();

                // 6. 执行任务
                FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
                executor.createJob(finalBuilder, progress -> {
                    if (progressConsumer != null && totalDurationNs > 0) {
                        double percentage = progress.out_time_ns / totalDurationNs;
                        progressConsumer.accept(Math.min(1.0, Math.max(0.0, percentage)));
                    }
                }).run();

            } catch (IOException e) {
                throw new RuntimeException("FFmpeg 任务执行失败: " + e.getMessage(), e);
            }
        }, executorService);
    }
}
