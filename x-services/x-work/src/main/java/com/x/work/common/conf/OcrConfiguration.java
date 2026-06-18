package com.x.work.common.conf;

import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;

/**
 * @author : xuemingqi
 * @since : 2024/3/6 11:24
 */
@Slf4j
@Configuration
public class OcrConfiguration {

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath(getTrainedDataPath());
        tesseract.setLanguage("chi_sim");
        return tesseract;
    }

    private String getTrainedDataPath() {
        String path = null;
        try {
            path = new DefaultResourceLoader().getResource("classpath:tessdata").getURL().getPath();
            if (SystemUtil.getOsInfo().isWindows()) {
                path = path.substring(1);
            }
        } catch (IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return path;
    }
}
